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



    @PostMapping("/users/{userId}/blogs")
    public ResponseEntity createBlog(@PathVariable Integer userId, @Valid @RequestBody Blog blog) {
        Blog createdBlog = blogService.createBlog(userId, blog);
        return ResponseEntity.status(200).body(createdBlog);
    }

    @GetMapping("/users/{userId}/blogs")
    public ResponseEntity<List<Blog>> getAllBlogsByUserId(@PathVariable Integer userId) {
        List<Blog> blogs = blogService.getAllBlogsByUserId(userId);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/users/{userId}/blogs/{blogId}")
    public ResponseEntity getBlogById(@PathVariable Integer userId, @PathVariable Integer blogId) {
        Blog blog = blogService.getBlogById(blogId, userId);
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/users/{userId}/blogs/title/{title}")
    public ResponseEntity getBlogByTitle(@PathVariable Integer userId, @PathVariable String title) {
        Blog blog = blogService.getBlogByTitle(title, userId);
        return ResponseEntity.ok(blog);
    }



    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity deleteBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId){
        blogService.deleteBlog(user.getId(), blogId);
        return ResponseEntity.status(200).body("blog deleted");
    }


    @PutMapping("/update/{blogId}")
    public ResponseEntity updateBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId , @RequestBody @Valid Blog blog){
        blogService.updateBlog(user.getId(), blogId, blog);
        return ResponseEntity.status(200).body("blog updated");
    }


}
