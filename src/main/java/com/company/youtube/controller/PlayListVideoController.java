package com.company.youtube.controller;

import com.company.youtube.dto.PlayListVideoDTO;
import com.company.youtube.enams.ProfileRole;
import com.company.youtube.service.PlayListVideoService;
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
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/playListVideo")
@RequiredArgsConstructor
@Api(tags = "playList Video")
public class PlayListVideoController {
    @Autowired
    private PlayListVideoService playListVideoService;

    @ApiOperation(value = "Create" ,notes = "Method used for create Playlist Video")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid PlayListVideoDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(playListVideoService.create(dto));
    }

    @ApiOperation(value = "Create" ,notes = "Method used for create Playlist Video")
    @PutMapping("/update/{pId}")
    public ResponseEntity<?> update(@RequestBody @Valid PlayListVideoDTO dto,
                                    HttpServletRequest request, @PathVariable Integer pId) {
        JwtUtil.getIdFromHeader(request, ProfileRole.OWNER);
        return ResponseEntity.ok(playListVideoService.update(pId,dto));
    }


    @ApiOperation(value = "Delete", notes = "Method used for deleted playlistVideo ")
    @DeleteMapping("/public")
    public ResponseEntity<String> delete(@RequestParam(value = "playlistId") String playlistId,
                                         @RequestParam(value = "videoId") String videoId,
                                         HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(playListVideoService.delete(pId, playlistId, videoId));
    }

    @ApiOperation(value = "Get By Playlist Id", notes = "Method used for Get By Playlist Id ")
    @GetMapping("/public/{playlistId}")
    public ResponseEntity<List<PlayListVideoDTO>> getByPlaylistId(@PathVariable("playlistId") String playlistId,
                                                                  HttpServletRequest request){
        JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(playListVideoService.getByPlaylistId(playlistId));
    }


}
