package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.gamerecommender.dto.FormUserProfile;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@Controller
@RequestMapping(value = "/user/profile")
public class UserProfileController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = { "", "/" })
	public String userProfile(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		model.addAttribute("user", user);
		
		if (user.getRole() == Role.DEVELOPER) {
			List<Game> gameList = gameService.findGamesByDevId(user.getId());
			model.addAttribute("gameList", gameList);
		}
		
		return "account-profile";
	}
	
	@GetMapping(value = "/edit")
	public String userProfileEditForm(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfileForm", new FormUserProfile());
		
		return "account-profile-edit";
	}
	
	@PostMapping(value = "/edit")
	public String updateUserProfile(@Valid @ModelAttribute("user") FormUserProfile userProfileForm,
			BindingResult bindingResult, Model model, HttpSession sessionObj) {

		User user = (User) sessionObj.getAttribute("user");
		user.setDisplayName(userProfileForm.getDisplayName());
		user.setDisplayImageUrl(userProfileForm.getDisplayImageUrl());
		user.setBiography(userProfileForm.getBiography());
		user.getProfile().setVisibilityStatus(userProfileForm.isVisibilityStatus());
		
		userService.updateUser(user);
		
		return "redirect:/user/profile";
	}
}
