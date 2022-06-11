package com.company.youtube.controller;

import com.company.youtube.dto.AttachDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.AttachService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/attach")
@Api(tags = "Attach")
@RequiredArgsConstructor
public class AttachController {
    @Autowired
    private AttachService attachService;


   //1. Create Attach (upload)
    @ApiOperation(value = "Upload", notes = "Method used for upload files")
    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        log.info("attach upload {}", file);
        return ResponseEntity.ok(attachService.upload(file));
    }

    //2. Get Attach By Id (Open)
    @ApiOperation(value = "Open", notes = "Method used for open files")
    @GetMapping(value = "/open/{key}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("key") String key) {
        log.info("/open/{key} {}",key );
        return attachService.open_general(key);
    }

    //3. Download Attach (Download)
    @ApiOperation(value = "Download", notes = "Method used for download files")
    @GetMapping("/download/{key}")
    public ResponseEntity<Resource> download(@PathVariable("key") String key) {
        log.info("/download/{key} {}", key);
        return attachService.download(key);
    }


    // 4. Attach pagination (ADMIN)
    //        id,origen_name,size,url
    @ApiOperation(value = "List", notes = "Method used for get list of files from database",
            authorizations = @Authorization(value = "JWT Token"))
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        log.info("LIST page={} size={}", page, size);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.paginationList(page, size));
    }

    // 5. Delete Attach (delete from db and system) (ADMIN)
    @ApiOperation(value = "Delete", notes = "Method used for delete files from local and database",
            authorizations = @Authorization(value = "JWT Token"))
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key, HttpServletRequest request) {
        log.info("DELETE {}", key);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(key));
    }
}
