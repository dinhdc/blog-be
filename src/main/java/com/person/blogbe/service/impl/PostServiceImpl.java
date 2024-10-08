package com.person.blogbe.service.impl;

import com.person.blogbe.entity.Post;
import com.person.blogbe.exception.ResourceNotFoundException;
import com.person.blogbe.payload.PostDto;
import com.person.blogbe.payload.PostResponse;
import com.person.blogbe.repository.PostRepository;
import com.person.blogbe.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to entity
        Post post = this.mapToEntity(postDto);
        Post newPost = this.postRepository.save(post);

        // convert entity to DTO
        return this.mapToDto(newPost);
    }


    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // create sort instance
        Sort.Direction sortDirection = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(sortDirection, sortBy);

        // create Pageable object
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = this.postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPost = posts.getContent();
        List<PostDto> postDtoList = listOfPost.stream().map(this::mapToDto).toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setData(postDtoList);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setTotalRecords(posts.getTotalElements());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id.toString()));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id.toString()));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post newPost = this.postRepository.save(post);
        return mapToDto(newPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id.toString()));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        return this.mapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto) {
        return this.mapper.map(postDto, Post.class);
    }
}
