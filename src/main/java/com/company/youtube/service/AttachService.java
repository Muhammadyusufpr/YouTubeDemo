package com.company.youtube.service;

import com.company.youtube.dto.AttachDTO;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.exception.AppBadRequestException;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.AttachRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    private String attachFolder;
    @Value("${server.domain.name}")
    private String domainName;


    public String toOpenURL(String id) {
        return domainName + "/attach/open_general/" + id;
    }
    public AttachDTO upload(MultipartFile file) {
        String pathFolder = getYmDString();
        File folder = new File(attachFolder + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String key = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename());

        AttachEntity entity = saveAttach(key, pathFolder, extension, file);
        AttachDTO dto = toDTO(entity);

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + pathFolder + "/" + key + "." + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setCratedDate(entity.getCreatedDate());
        dto.setOrigenName(entity.getOrigenName());
        dto.setPath(entity.getPath());
        dto.setUrl(domainName + "/attach/download/" + entity.getId());
        return dto;
    }

    private AttachEntity saveAttach(String key, String pathFolder, String extension, MultipartFile file) {
        AttachEntity entity = new AttachEntity();
        entity.setId(key);
        entity.setPath(pathFolder);
        entity.setOrigenName(file.getOriginalFilename());
        entity.setExtension(extension);
        entity.setSize(file.getSize());
        attachRepository.save(entity);
        return entity;
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public byte[] open_general(String key) {
        byte[] data;

        try {
            AttachEntity entity = get(key);
            String path = entity.getPath();
            Path file = Paths.get(attachFolder + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(()->{
            log.error("Attach Not Found!");
            throw new ItemNotFoundException("Attach Not Found!");
        });
    }

    public ResponseEntity<Resource> download(String key) {
        try {
            AttachEntity entity = get(key);
            String path = entity.getPath() + "/" + key + "." + entity.getExtension();
            Path file = Paths.get(attachFolder + path);
            Resource resource =  new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename\"" + entity.getOrigenName() + "\"")
                        .body(resource);
            }else
                log.error("Could not read the file!"); throw new RuntimeException("Could not read the file!");

        } catch (MalformedURLException e) {
            log.error("Error: "); throw new RuntimeException("Error:" + e.getMessage());

        }
    }

    public Boolean delete(String key) {
        AttachEntity entity = get(key);

        File file = new File(attachFolder + entity.getPath() +
                "/" + entity.getId() + "." + entity.getExtension());

        if (file.delete()) {
            attachRepository.deleteById(key);
            return true;
        } else log.error("Could not read the file!"); throw new AppBadRequestException("Could not read the file!");

    }

    public List<AttachDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<AttachDTO> dtoList = new LinkedList<>();
        attachRepository.findAll(pageable).stream().forEach(attachEntity -> {
            dtoList.add(toDTO(attachEntity));
        });

        return dtoList;
    }

    public AttachDTO toOpenURLDTO(String id) {
        return new AttachDTO(domainName + "/attach/open_general/" + id);
    }
}
