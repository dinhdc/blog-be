package com.person.blogbe.service.impl;

import com.person.blogbe.entity.Post;
import com.person.blogbe.exception.ResourceNotFoundException;
import com.person.blogbe.payload.PostDto;
import com.person.blogbe.repository.PostRepository;
import com.person.blogbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post newPost = this.postRepository.save(post);

        // convert entity to DTO
        PostDto postRes = new PostDto();
        postRes.setTitle(newPost.getTitle());
        postRes.setContent(newPost.getContent());
        postRes.setDescription(newPost.getDescription());
        postRes.setId(newPost.getId());
        return postRes;
    }

    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = this.postRepository.findAll();
        return posts.stream().map(this::mapToDto).toList();
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post newPost = this.postRepository.save(post);
        return mapToDto(newPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }
}
