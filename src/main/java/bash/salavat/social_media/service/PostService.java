package bash.salavat.social_media.service;

import bash.salavat.social_media.dto.PostCreateDTO;
import bash.salavat.social_media.model.Post;
import bash.salavat.social_media.model.User;
import bash.salavat.social_media.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    public Post createPost(PostCreateDTO postDTO) {
        Optional<User> authorOptional = userService.getCurrentUser();
        User author = authorOptional.get();

        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setAuthor(author);
        post.setImageUrl(postDTO.getImageUrl());

        return postRepository.save(post);
    }

    public Page<Post> getFeed(Pageable pageable) {
        return postRepository.findAllWithAuthors(pageable);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthor().equals(userService.getCurrentUser())) {
            throw new AccessDeniedException("You can't delete this post");
        }

        postRepository.delete(post);
    }
}