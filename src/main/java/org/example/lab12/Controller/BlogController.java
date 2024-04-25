package org.example.lab12.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab12.Model.Blog;
import org.example.lab12.Model.User;
import org.example.lab12.Service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;



    @PostMapping("/users/blogs")
    public ResponseEntity createBlog(@AuthenticationPrincipal User user,@RequestBody @Valid Blog blog) {
        Blog createdBlog = blogService.createBlog(user.getId(), blog);
        return ResponseEntity.status(200).body(createdBlog);
    }

    @GetMapping("/users/blogs")
    public ResponseEntity<List<Blog>> getAllBlogsByUserId(@AuthenticationPrincipal User user) {
        List<Blog> blogs = blogService.getAllBlogsByUserId(user.getId());
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/users/blogs/{blogId}")
    public ResponseEntity getBlogById(@AuthenticationPrincipal User user, Integer blogId) {
        Blog blog = blogService.getBlogById(user.getId(), blogId);
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/users/blogs/title/{title}")
    public ResponseEntity getBlogByTitle(@AuthenticationPrincipal User user, String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(user.getId(), title));

    }



    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity deleteBlog(@AuthenticationPrincipal User user, Integer blogId){
        blogService.deleteBlog(user.getId(), blogId);
        return ResponseEntity.status(200).body("blog deleted");
    }


    @PutMapping("/update/{blogId}")
    public ResponseEntity updateBlog(@AuthenticationPrincipal User user, Integer blogId , @RequestBody @Valid Blog blog){
        blogService.updateBlog(user.getId(), blogId, blog);
        return ResponseEntity.status(200).body("blog updated");
    }


}
