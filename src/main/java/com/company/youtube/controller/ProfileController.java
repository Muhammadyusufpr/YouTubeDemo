package com.company.youtube.controller;

import com.company.youtube.dto.AttachDTO;
import com.company.youtube.dto.ProfileDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.ProfileService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Api(tags = "profile")
public class ProfileController {

    private final ProfileService profileService;

    // 8. Create Profile (ADMIN)
    //        (id,name,surname,email,Role(ADMIN,MODERATOR))
    @ApiOperation(value = "create", notes = "Method used for create profile")
    @PostMapping("/adm/create")
    public ResponseEntity<?> createProfile(@RequestBody @Valid ProfileDTO dto,
                                           HttpServletRequest request) {
        log.info("Profile {}", dto);
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @ApiOperation(value = "get", notes = "Method used for get profile by id")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    //5. Update Profile Detail(name,surname) ✅
    @ApiOperation(value = "update", notes = "Method used for update profile detail")
    @PutMapping("/updateDetail/{id}")
    public ResponseEntity<?> updateDetail(@PathVariable("id") Integer id,
                                          @RequestBody @Valid ProfileDTO dto) {
        log.info("Profile {}", dto);
        return ResponseEntity.ok(profileService.updateDetail(id, dto));
    }

    @ApiOperation(value = "Delete", notes = "Method used for delete profile")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                           HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.delete(id));
    }

    //6. Update Profile Attach (main_photo) (delete old attach) ✅
    @ApiOperation(value = "update image", notes = "Method used for update image")
    @PostMapping("/image")
    public ResponseEntity<?> updateImage(@RequestBody AttachDTO image,
                                         HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request);
        try {
            return ResponseEntity.ok(profileService.updateImage(image.getId(), pId));
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Attach not found");
        }
    }
}
