package bash.salavat.social_media.controller;

import bash.salavat.social_media.dto.ProfileEditDTO;
import bash.salavat.social_media.dto.RegistrationDTO;
import bash.salavat.social_media.model.User;
import bash.salavat.social_media.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.loadUserByUsername(authentication.getName());

        ProfileEditDTO profileEditDTO = new ProfileEditDTO();
        profileEditDTO.setFirstName(currentUser.getFirstName());
        profileEditDTO.setLastName(currentUser.getLastName());
        profileEditDTO.setBio(currentUser.getBio());
        profileEditDTO.setProfilePictureUrl(currentUser.getProfilePictureUrl());

        model.addAttribute("profileEditDTO", profileEditDTO);
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@Valid @ModelAttribute("profileEditDTO") ProfileEditDTO profileEditDTO,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return "edit-profile";
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userService.updateUserProfile(authentication.getName(), profileEditDTO);
            return "redirect:/profile?success";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating profile: " + e.getMessage());
            return "edit-profile";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.loadUserByUsername(username);

        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegistrationDTO registrationDTO,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(registrationDTO);
            return "redirect:/login?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}