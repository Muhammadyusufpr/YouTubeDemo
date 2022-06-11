package com.company.youtube.controller;

import com.company.youtube.dto.CategoryDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.CategoryService;
import com.company.youtube.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Api(tags = "Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //1. Create Category (ADMIN)
    @ApiOperation(value = "Create", notes = "Method used for create category",
            authorizations = @Authorization(value = "JWT Token"))
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("Category {}", dto);
        return ResponseEntity.ok(categoryService.create(dto));
    }


    // 2. Update Category (ADMIN)
    @ApiOperation(value = "Update", notes = "Method used for update category",
            authorizations = @Authorization(value = "JWT Token"))
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody CategoryDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("Category {}", dto);
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    //3. Delete Category (ADMIN)
    @ApiOperation(value = "delete", notes = "Method used for delete category by Id")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("Category {}");
        return ResponseEntity.ok(categoryService.delete(id));
    }

    //  5. Category List
    @ApiOperation(value = "list", notes = "Method used for pagination list")
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
//        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.paginationList(page, size));
    }
}
