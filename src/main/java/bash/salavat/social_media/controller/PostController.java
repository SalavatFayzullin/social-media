package bash.salavat.social_media.controller;

import bash.salavat.social_media.dto.PostCreateDTO;
import bash.salavat.social_media.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new PostCreateDTO());
        return "create-post";
    }

    @PostMapping("/posts")
    public String createPost(@Valid @ModelAttribute("post") PostCreateDTO postDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "create-post";
        }

        postService.createPost(postDTO);
        return "redirect:/feed";
    }

    @GetMapping("/feed")
    public String showFeed(
            @PageableDefault(size = 10) Pageable pageable,
            Model model
    ) {
        model.addAttribute("posts", postService.getFeed(pageable));
        return "feed";
    }
}