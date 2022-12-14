package com.company.youtube.controller;

import com.company.youtube.dto.ReportDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.ReportService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/report")
@Api(tags = "Report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @ApiOperation(value = "Create", notes = "Method used for create report")
    @PostMapping("/public")
    public ResponseEntity<ReportDTO> create(@RequestBody @Valid ReportDTO dto,
                                            HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(reportService.create(pId, dto));
    }

    @ApiOperation(value = "Get Pagination List", notes = "Method used for Get Pagination List for admin")
    @GetMapping("/adm/listPagination")
    public ResponseEntity<PageImpl<ReportDTO>> getListPagination(HttpServletRequest request,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "3") int size){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(reportService.getListPagination(page, size));
    }

    @ApiOperation(value = "Get by reportId", notes = "Method used for Get by report id")
    @GetMapping("/adm/{reportId}")
    public ResponseEntity<ReportDTO> getById(@PathVariable("reportId") Integer reportId,
                                     HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(reportService.getByReportId(reportId));
    }

    @ApiOperation(value = "Get by profile Id", notes = "Method used for Get list by profile id for admin")
    @GetMapping("/adm/profile/{profileId}")
    public ResponseEntity<PageImpl<ReportDTO>> getListByUserId(@PathVariable("profileId") Integer profileId,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "3") int size,
                                             HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(reportService.getByProfileId(profileId, page, size));
    }

}
