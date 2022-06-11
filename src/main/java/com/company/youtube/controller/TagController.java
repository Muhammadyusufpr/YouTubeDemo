package com.company.youtube.controller;

import com.company.youtube.dto.TagDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.TagService;
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
@RequestMapping("/tag")
@RequiredArgsConstructor
@Api(tags = "Tag")
public class TagController {

    @Autowired
    private TagService tagService;

    //1. Create Tag
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TagDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(tagService.create(dto));
    }


    //2. Update Category (ADMIN)
    @PutMapping("/update") 
    public ResponseEntity<?> update(@RequestBody TagDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        log.info("Tag {}", dto);
        return ResponseEntity.ok(tagService.update(dto));
    }

    //3. Delete Category (ADMIN)
    @DeleteMapping("/delete{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    HttpServletRequest request) {
        Integer idFromHeader = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        log.info("Tag {}", id);
        return ResponseEntity.ok(tagService.delete(id));
    }

    //5. Tag List
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(tagService.paginationList(page, size));
    }
}
