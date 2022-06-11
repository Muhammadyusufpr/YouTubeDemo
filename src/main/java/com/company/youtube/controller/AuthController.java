package com.company.youtube.controller;

import com.company.youtube.dto.AuthDTO;
import com.company.youtube.dto.RegistrationDTO;
import com.company.youtube.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Api(tags = "Authorization")
public class AuthController {

    private final AuthService authService;


    @ApiOperation(value = "Login", notes = "Method used for login and getting token")
    @PostMapping("/login")
    public ResponseEntity<?> create(@RequestBody @Valid AuthDTO dto) {
        log.info("Authorization {}", dto);
        return ResponseEntity.ok(authService.login(dto));
    }


    @ApiOperation(value = "Registration", notes = "Method used for registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        log.info("RegistrationDTO {}", dto);
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "Email Verification", notes = "Method used for email verifier")
    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        log.info("Verification: {}", jwt);
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}
