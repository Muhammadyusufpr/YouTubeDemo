package com.company.youtube.controller;

import com.company.youtube.dto.VideoDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.enams.VideoStatus;
import com.company.youtube.service.VideoService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Api(tags = "Video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    //1. Create Video (USER)
    @ApiOperation(value = "Create", notes = "Method used for create Video")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody VideoDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(videoService.create(dto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable("id") String id) {
        return ResponseEntity.ok(videoService.getById(id));
    }


    //2. Update Video Detail (USER and OWNER)
    @ApiOperation(value = "put", notes = "Method used for update Video")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody @Valid VideoDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(videoService.update(id, dto, pId));
    }


    @ApiOperation(value = "delete", notes = "Method used for delete Video")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String vId,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(videoService.delete(vId, pId));
    }

    //4. Increase video_view Count by key
    @ApiOperation(value = "get", notes = "Method used for increase Video")
    @GetMapping("/increase/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id,
                                 HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(videoService.get(id));
    }

    // 5. Get Video Pagination by CategoryId
    @ApiOperation(value = "get", notes = "Method used for Get Video Pagination by CategoryId")
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> categoryPagination(@PathVariable("categoryId") String categoryId,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(videoService.getCategoryPagination(categoryId, page, size));
    }

    //6. Search video by Title
    @ApiOperation(value = "get", notes = "Method used for Search video by Title")
    @GetMapping("/{title}")
    public ResponseEntity<?> searchVideoByTitle(@PathVariable("title") String title,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(videoService.getSearchVideoByTitle(title, page, size));
    }

    //Get Video By key
    @ApiOperation(value = "get", notes = "Method used for Get Video By key")
    @GetMapping("/{vId}")
    public ResponseEntity<?> getVideoById(@PathVariable("vId") String vId,
                                          HttpServletRequest request) {
        return ResponseEntity.ok(videoService.getVideoById(vId, request));
    }




}
