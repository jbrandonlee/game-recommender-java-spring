package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.User;

@Controller
@RequestMapping(value = "/user/profile")
public class UserProfileController {
	
	@GetMapping(value = { "", "/" })
	public String userProfile(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		// Profile profile = profileService.getProfileByUser(user);
		model.addAttribute("user", user);
		// model.addAttribute("profile", profile);
		return "account-profile";
	}
	
	@GetMapping(value = "/edit")
	public String userProfileEditForm(Model model, HttpSession sessionObj) {
		return "account-profile-edit";
	}
	
	@PostMapping(value = "/edit")
	public String updateUserProfile(Model model, HttpSession sessionObj) {
		return "account-profile";
	}
}
