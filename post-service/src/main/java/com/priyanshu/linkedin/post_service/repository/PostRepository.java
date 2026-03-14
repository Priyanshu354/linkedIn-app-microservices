package com.priyanshu.linkedin.post_service.repository;

import com.priyanshu.linkedin.post_service.dto.PostDto;
import com.priyanshu.linkedin.post_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
}
