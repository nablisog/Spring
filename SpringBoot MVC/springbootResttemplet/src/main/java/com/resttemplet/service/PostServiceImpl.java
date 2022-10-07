package com.resttemplet.service;

import com.resttemplet.model.Comment;
import com.resttemplet.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostServiceImpl implements PostService{
    private final static String URL = "https://jsonplaceholder.typicode.com";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Post[] getAllPosts() {
        Post[] post =  restTemplate.getForObject(URL + "/posts", Post[].class);
        return post;
    }

    @Override
    public Comment[] getAllCommentsforAPosts(Long id) {
        Comment[] comments =  restTemplate.getForObject(URL + "/posts/" + id + "/comments", Comment[].class);
        return comments;
    }

    @Override
    public Post createPost(Post post) {
        Post model = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Post> entity = new HttpEntity<>(post,headers);
        ResponseEntity<Post> newPost =
                restTemplate.postForEntity(URL+"/posts" ,entity,Post.class);

        if (newPost.getStatusCode() == HttpStatus.CREATED) {
            model = newPost.getBody();
        }
        return model;
    }

    @Override
    public Post updatePost(Post post,Long id) {
        Post model = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Post> entity = new HttpEntity<>(post,headers);
        ResponseEntity<Post> updated =
                restTemplate.exchange(URL+"/posts/{id}",HttpMethod.PUT ,entity,Post.class,id);

        if (updated.getStatusCode() == HttpStatus.OK) {
            model = updated.getBody();
        }
        return model;
    }

    @Override
    public void deletePost(Long id) {
        restTemplate.delete(URL+"/post/{id}",id);
    }
}
