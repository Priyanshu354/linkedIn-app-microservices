package com.priyanshu.linkedin.post_service.service;

import com.priyanshu.linkedin.post_service.dto.PostCreateRequestDto;
import com.priyanshu.linkedin.post_service.dto.PostDto;
import com.priyanshu.linkedin.post_service.entity.Post;
import com.priyanshu.linkedin.post_service.event.PostCreatedEvent;
import com.priyanshu.linkedin.post_service.exception.ResourceNotFoundException;
import com.priyanshu.linkedin.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postRepository.save(post);

        PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                .postId(savedPost.getId())
                .creatorId(userId)
                .content(savedPost.getContent()).build();

        kafkaTemplate.send("post-created-topic", postCreatedEvent);

        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId, "post"));
        return modelMapper.map(post, PostDto.class);
    }


    public List<PostDto> getAllPost(Long userId) {
        List<Post> allPosts = postRepository.findAllByUserId(userId);
        return allPosts
                .stream()
                .map((ele) -> modelMapper.map(ele, PostDto.class))
                .collect(Collectors.toList());
    }
}
