package com.company.youtube.controller;

import com.company.youtube.dto.ChannelDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.ChannelService;
import com.company.youtube.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
@Api(tags = "Channel")
public class ChannelController {

    private final ChannelService channelService;

    //1. Create Channel (USER)
    @ApiOperation(value = "create", notes = "Method used for create channel")
    @PostMapping("/public/create")
    public ResponseEntity<?> create(@RequestBody ChannelDTO dto,
                                    HttpServletRequest request) {
        log.info("Channel {}", dto);
        Integer pId = JwtUtil.getIdFromHeader(request,  ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(channelService.create(dto, pId));
    }

    //2. Update Channel ( USER and OWNER)
    @ApiOperation(value = "Update", notes = "Method used for update channel")
    @PutMapping("/public/update")
    public ResponseEntity<?> update(@RequestBody ChannelDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(channelService.update(dto, pId));
    }

    //3. Update Channel photo ( USER and OWNER)
    @ApiOperation(value = "update photo", notes = "Method used for update photo")
    @PutMapping("/public/{cid}/updatePhoto/{aid}")
    public ResponseEntity<?> updatePhoto(@PathVariable("aid") String aid,
                                         HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request,  ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(channelService.updatePhoto(aid, pId));
    }

    //4. Update Channel banner ( USER and OWNER)
    @ApiOperation(value = "update banner", notes = "Method used for update banner")
    @PutMapping("/public/{cid/updateBanner/{bid}")
    public ResponseEntity<?> updateBanner(@PathVariable("bid") String bid,
                                          HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request,  ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(channelService.updateBanner(bid, pId));
    }

    // 5. Channel Pagination (ADMIN)
    @ApiOperation(value = "list", notes = "Method used for admin list")
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(channelService.paginationList(page, size));
    }


}
