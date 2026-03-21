package com.priyanshu.linkedin.post_service.controller;

import com.priyanshu.linkedin.post_service.auth.UserContextHoler;
import com.priyanshu.linkedin.post_service.service.PostLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    ResponseEntity<Void> likePost(@PathVariable Long postId, HttpServletRequest request) {
        postLikeService.likePost(postId, UserContextHoler.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> disLikePost(@PathVariable Long postId, HttpServletRequest request) {
        postLikeService.disLikePost(postId, UserContextHoler.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }
}
