package com.company.youtube.controller;

import com.company.youtube.dto.CommentDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.CommentService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
@Api(tags = "Comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //1. Crate Comment (USER)
    @ApiOperation(value = "create", notes = "Method used for create comment")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CommentDTO dto) {
        return ResponseEntity.ok(commentService.create(dto));
    }

    //2. Update Comment (USER AND OWNER)
    @ApiOperation(value = "update", notes = "Method used for update comment")
    @PutMapping("/update/{cId}")
    public ResponseEntity<?> update(@PathVariable("cId") String cId,
                                    @RequestBody @Valid CommentDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(commentService.update(cId, dto));
    }

    //3. Delete Comment (USER AND OWNER, ADMIN)
    @ApiOperation(value = "delete", notes = "Method used for delete comment")
    @PutMapping("/delete/{cId}")
    public ResponseEntity<?> delete(@PathVariable("cId") String cId,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.delete(cId));
    }


    // 4. Comment List Pagination (ADMIN)
    @ApiOperation(value = "list", notes = "Method used get pagination")
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.list(page, size));
    }


    //5. Comment List By profileId(ADMIN)
    @ApiOperation(value = "list", notes = "Method used get pagination for ADMIN")
    @GetMapping("/list/{pId}")
    public ResponseEntity<?> listByProfileId(@PathVariable("pId") Integer pId,
                                             HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.listByProfileId(pId));
    }
}
