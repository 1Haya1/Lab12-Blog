package org.example.lab12.Service;

import lombok.RequiredArgsConstructor;
import org.example.lab12.Api.ApiException;
import org.example.lab12.Model.Blog;
import org.example.lab12.Model.User;
import org.example.lab12.Repository.AuthRepository;
import org.example.lab12.Repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    private final AuthRepository authRepository;


    public Blog createBlog(Integer userId, Blog blog) {
        User user = authRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        blog.setUser(user);
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlogsByUserId(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        return blogRepository.findAllBlogByUserId(userId);
    }

    public Blog getBlogById(Integer blogId, Integer userId) {
        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null || !blog.getUser().getId().equals(userId))
            throw new ApiException("Blog not found or does not belong to the specified user");

        return blogRepository.findBlogById(blogId);
    }

   public Blog getBlogByTitle(Integer userid , String title ){
        User user = authRepository.findUserById(userid);
        Blog blog = blogRepository.findBlogByTitle(title);

        if (blog.getUser().equals(user)) {
            return blog;
        }
        return null;
    }



    public void deleteBlog(Integer userid , Integer blogId ){
        User user = authRepository.findUserById(userid);
        Blog blog = blogRepository.findBlogById(blogId);

        if (blog.getUser().equals(user) ) {
            blogRepository.delete(blog);
        }
    }



    public void updateBlog(Integer userid , Integer blogId , Blog blog ){
        User user = authRepository.findUserById(userid);
        Blog blog1 = blogRepository.findBlogById(blogId);

        if (blog.getUser().equals(user)  ) {
            blog1.setTitle(blog.getTitle());
            blog1.setBody(blog.getBody());
            blogRepository.save(blog1);
        }


}}
