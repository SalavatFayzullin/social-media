package bash.salavat.social_media.controller;

import bash.salavat.social_media.model.User;
import bash.salavat.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/top-profiles")
    public String showTopProfiles(Model model) {
        List<User> topUsers = userService.getTopUsers(10); // Top 10 users
        model.addAttribute("topUsers", topUsers);
        return "top-profiles";
    }
}
