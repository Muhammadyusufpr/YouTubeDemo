package com.company.youtube.controller;

import com.company.youtube.enams.LikeType;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.VideoLikeService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/videoLike")
@RequiredArgsConstructor
@Api(tags = "Video Like")
public class VideoLikeController {
    @Autowired
    private VideoLikeService videoLikeService;

    @PostMapping("/like/{id}")
    public ResponseEntity<?> createLike(@PathVariable("id") String videoId,
                                        HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(videoLikeService.create(videoId, LikeType.LIKE, pId));
    }

    @PostMapping("/disLike/{id}")
    public ResponseEntity<?> createDisLike(@PathVariable("id") String videoId,
                                           HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(videoLikeService.create(videoId, LikeType.DISLIKE, pId));
    }

    @DeleteMapping("/deleteLike/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable("id") String videoId,
                                        HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(videoLikeService.delete(videoId, LikeType.LIKE, pId));
    }

    @DeleteMapping("/deleteDisLike/{id}")
    public ResponseEntity<?> deleteDisLike(@PathVariable("id") String videoId,
                                        HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(videoLikeService.delete(videoId, LikeType.DISLIKE, pId));
    }

}
