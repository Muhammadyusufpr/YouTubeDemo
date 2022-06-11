package com.company.youtube.controller;

import com.company.youtube.dto.VideoTagDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.VideoTagService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/videoTag")
@RequiredArgsConstructor
@Api(tags = "Video Tag")
public class VideoTagController {
    @Autowired
    private VideoTagService videoTagService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid VideoTagDTO dto,
                                     HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(videoTagService.create(dto));
    }

    @PutMapping("/{vId}/delete/{tId}")
    public ResponseEntity<?> delete(@PathVariable("tId") String tId,
                                    HttpServletRequest request,
                                    @PathVariable("vId") String vId) {
        JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(videoTagService.delete(tId, vId));
    }
}
