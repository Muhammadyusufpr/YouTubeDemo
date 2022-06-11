package com.company.youtube.controller;


import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.EmailService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Api(tags = "Email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "list", notes = "Method used for email pagination")
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "5") int size,
                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(emailService.paginationList(page, size));
    }

    @ApiOperation(value = "delete", notes = "Method used for delete email")
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(emailService.delete(id));
    }
}
