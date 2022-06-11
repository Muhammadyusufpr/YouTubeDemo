package com.company.youtube.service;

import com.company.youtube.dto.AttachDTO;
import com.company.youtube.dto.AuthDTO;
import com.company.youtube.dto.ProfileDTO;
import com.company.youtube.dto.RegistrationDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.enams.ProfileStatus;
import com.company.youtube.entity.AttachEntity;
import com.company.youtube.entity.ProfileEntity;
import com.company.youtube.exception.AppBadRequestException;
import com.company.youtube.exception.AppForbiddenException;
import com.company.youtube.exception.EmailAlreadyExistsException;
import com.company.youtube.exception.PasswordOrEmailWrongException;
import com.company.youtube.repository.ProfileRepository;
import com.company.youtube.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AttachService attachService;

    public ProfileDTO login(AuthDTO dto) {
        String password = DigestUtils.md5Hex(dto.getPassword());
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndPassword(dto.getEmail(), password);
        if (optional.isEmpty()) {
            throw new PasswordOrEmailWrongException("Password or email wrong!");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppForbiddenException("No Access");
        }

        ProfileDTO profile = new ProfileDTO();

        profile.setEmail(entity.getEmail());
        profile.setName(entity.getName());
        profile.setSurname(entity.getSurname());
        profile.setJwt(JwtUtil.encode(entity.getId(),entity.getRole()));

        //image
        AttachEntity image = entity.getAttach();
        if (image != null) {
            AttachDTO imageDTO = new AttachDTO();
            imageDTO.setUrl(attachService.toOpenURL(image.getId()));
        }

        return profile;
    }

    public void registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());

        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(ProfileRole.OWNER);
        profileRepository.save(entity);

       /* Thread thread = new Thread() {
            @Override
            public void run() {
                sendVerificationEmail(entity);
            }
        };
        thread.start();*/


    }

    private void sendVerificationEmail(ProfileEntity entity) {
        StringBuilder builder = new StringBuilder();
        String jwt = JwtUtil.encode(entity.getId());
        builder.append("Salom bormsin \n");
        builder.append("To verify your registration click to next link.");
        builder.append("http://localhost:8080/auth/verification/").append(jwt);
        emailService.send(entity.getEmail(), "Activate Your Registration", builder.toString());
    }

    public void verification(String jwt) {
        Integer userId = null;
        try {
            userId = JwtUtil.decodeAndGetId(jwt);
        } catch (JwtException e) {
            throw new AppBadRequestException("Verification not completed");
        }

        profileRepository.updateStatus(ProfileStatus.ACTIVE, userId);
    }
}
