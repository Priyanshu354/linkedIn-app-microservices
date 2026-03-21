package com.priyanshu.linkedin.post_service.controller;

import com.priyanshu.linkedin.post_service.auth.UserContextHoler;
import com.priyanshu.linkedin.post_service.dto.PostCreateRequestDto;
import com.priyanshu.linkedin.post_service.dto.PostDto;
import com.priyanshu.linkedin.post_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto, HttpServletRequest request) {
        PostDto postDto = postService.createPost(postCreateRequestDto, UserContextHoler.getCurrentUserId());
        return new ResponseEntity<>(postDto,HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/allPosts")
    ResponseEntity<List<PostDto>> getAllPost(@PathVariable Long userId) {
        List<PostDto> allPosts = postService.getAllPost(userId);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

}
