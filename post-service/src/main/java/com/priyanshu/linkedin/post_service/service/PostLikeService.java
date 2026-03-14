package com.priyanshu.linkedin.post_service.service;

import com.priyanshu.linkedin.post_service.entity.PostLike;
import com.priyanshu.linkedin.post_service.exception.BadRequestException;
import com.priyanshu.linkedin.post_service.exception.ResourceNotFoundException;
import com.priyanshu.linkedin.post_service.repository.LikeRepository;
import com.priyanshu.linkedin.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public void likePost(Long postId, Long userId) {
        log.info("Attempting to like the post with id : {}", postId);
        boolean exist = postRepository.existsById(postId);

        if(!exist){
            throw new ResourceNotFoundException(postId, "post");
        }

        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId, postId);

        if(alreadyLiked) {
            throw new BadRequestException("Post is already liked");
        }

        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);

        likeRepository.save(postLike);
        log.info("Post Like Successfully with id : {}", postId);
    }

    public void disLikePost(Long postId, long userId) {
//        log.info("Attempting to Dislike the post with id : {}", postId);
//        boolean exist = postRepository.existsById(postId);
//
//        if(!exist){
//            throw new ResourceNotFoundException(postId, "post");
//        }

        PostLike postLike = likeRepository.findByUserIdAndPostId(userId, postId).orElseThrow(() -> new BadRequestException("Post is not liked"));

        likeRepository.delete(postLike);
        log.info("Post DisLike Successfully with id : {}", postId);
    }
}
