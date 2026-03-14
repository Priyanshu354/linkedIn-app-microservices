package com.priyanshu.linkedin.post_service.dto;

public class PostCreateRequestDto {

    private String content;

    public PostCreateRequestDto() {
    }

    public PostCreateRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}