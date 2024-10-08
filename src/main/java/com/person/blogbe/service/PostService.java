package com.person.blogbe.service;

import com.person.blogbe.payload.PostDto;
import com.person.blogbe.payload.PostResponse;


public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(Long id, PostDto postDto);
    void deletePost(Long id);
}
