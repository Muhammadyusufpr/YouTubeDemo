package com.company.youtube.service;

import com.company.youtube.dto.ProfileDTO;
import com.company.youtube.enams.ProfileStatus;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.exception.EmailAlreadyExistsException;
import com.company.youtube.exception.ItemNotFoundException;
import com.company.youtube.repository.ProfileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    AttachService attachService;

    public ProfileDTO create(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("email already exists");
        }

        ProfileEntity entity = new ProfileEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);
        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileDTO getById(Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Not found!"));
        if (!entity.getVisible()) {
            throw new ItemNotFoundException("Not Found!");
        }
        return toDTO(entity);
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileDTO updateDetail(Integer id, ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("This Email already used!");
        }

        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Not Found!"));
        if (!entity.getVisible()) {
            throw new ItemNotFoundException("Not Found!");
        }

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUpdatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        return toDTO(entity);
    }

    public Boolean delete(Integer id) {
        ProfileEntity entity = profileRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Not Found"));
        if (!entity.getVisible()) {
            throw new ItemNotFoundException("Not Found!");
        }

        profileRepository.updateVisible(false, id);

        return true;
    }
    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not Found!"));
    }

    public ProfileDTO toDTOForReport(Integer profileId){
        ProfileEntity entity = get(profileId);
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setImage(attachService.toOpenURLDTO(entity.getAttachId()));
        return dto;
    }

    public boolean updateImage(String attachId, Integer pId) {
        ProfileEntity profileEntity = get(pId);

        if (profileEntity.getAttach() != null) {
            attachService.delete(profileEntity.getAttach().getId());
        }
        profileRepository.updateAttach(attachId, pId);
        return true;
    }
}
