package com.company.youtube.controller;

import com.company.youtube.dto.PlayListDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.PlayListService;
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
@RequestMapping("/playList")
@RequiredArgsConstructor
@Api(tags = "playList")
public class PlayListController {
    @Autowired
    private PlayListService playListService;

    //1. Create Playlist (USER)
    @ApiOperation(value = "create", notes = "Method used for create PlayList")
    @PostMapping("/public/{channelId}")
    public ResponseEntity<?> create(@PathVariable("channelId") String channelId,
                                    @RequestBody @Valid PlayListDTO dto,
                                    HttpServletRequest request) {
        log.warn("PlayListDTO {}", dto);
        return ResponseEntity.ok(playListService.create(channelId, dto, JwtUtil.getIdFromHeader(request)));
    }

    //2. Update Playlist(USER and OWNER)
    @ApiOperation(value = "update", notes = "Method used for update PlayList")
    @PutMapping("/public/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody PlayListDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(playListService.update(id, dto));
    }

    //4. Delete Playlist (USER and OWNER, ADMIN)
    @ApiOperation(value = "delete", notes = "Method used for delete PlayList")
    @DeleteMapping("/public/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(playListService.delete(id));
    }

    //3. Change Playlist Status (USER and OWNER)
    @ApiOperation(value = "change", notes = "Method used for change status")
    @PutMapping("/public/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER, ProfileRole.USER);
        return ResponseEntity.ok(playListService.changeStatus(id));
    }

    // 5. Playlist Pagination (ADMIN)
    @ApiOperation(value = "list", notes = "Method used for pagination")
    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "3") int size,
                                        HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(playListService.pagination(page, size));
    }

    //Playlist Pagination (ADMIN) (order by order number desc) (ADMIN)
    @ApiOperation(value = "get", notes = "Method used for order by number ADMIN")
    @GetMapping("/adm/{userId}/pagination/{orderNum}")
    public ResponseEntity<?> listByUserId(@PathVariable("userId") Integer userId,
                                          @PathVariable("orderNum") Integer orderNum,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "3") int size,
                                          HttpServletRequest request) {

        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(playListService.listByUserId(userId, orderNum, page, size));
    }

    // 7 Get User Playlist (order by order number desc) (murojat qilgan user ni)
    @ApiOperation(value = "get", notes = "Method used for Get User Playlist")
    @GetMapping("/public/user/playList")
    public ResponseEntity<?> getUserPlaylist(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "3") int size,
                                             HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(playListService.getUserPlayList(pId, page, size));
    }


    // 8  Get Channel Play List By ChannelKey
    // (order by order_num desc) (only Public)
    @ApiOperation(value = "get", notes = "Method used for Get Channel Play List By ChannelKey")
    @GetMapping("/public/playList/{channelId}")
    public ResponseEntity<?> getChannelPlayListByChannelId(@PathVariable("channelId") String channelId,

                                                           HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(playListService.getChannelPlayListByChannelId(channelId));
    }


}
