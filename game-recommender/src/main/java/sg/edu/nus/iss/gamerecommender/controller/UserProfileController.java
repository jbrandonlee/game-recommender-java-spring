package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.gamerecommender.dto.FormUserProfile;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.PostService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@Controller
@RequestMapping(value = "/user/profile")
public class UserProfileController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	// TODO: Implement Add Genres on User EditProfile
	// TODO: Implement UI & APIs for ADD_FRIEND, FOLLOW_DEV, FOLLOW_GAME
	// TODO: Implement Limit(5) for ItemLists on Profile (in Thymeleaf)
	// TODO: Implement User Activity View (Posts)
	// TODO: Implement Individual ViewAll Pages
	
	@GetMapping(value = { "", "/" })
	public String ownerProfile(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		return "redirect:/user/profile/" + user.getId();
	}
	
	@GetMapping(value = "/{id}")
	public String userProfile(@PathVariable("id") Integer userId, Model model, HttpSession sessionObj) {
		User sessionUser = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		User profileUser = userService.findUserById(userId);
		boolean isProfileOwner = (sessionUser.getId() == profileUser.getId());
		boolean isProfileVisible = profileUser.getProfile().isVisibilityStatus(); 
		
		model.addAttribute("user", profileUser);
		model.addAttribute("isProfileOwner", isProfileOwner);
		
		if(!isProfileOwner && !isProfileVisible) {
			return "profile-hidden";
		}
		
		if (profileUser.getRole() == Role.GAMER) {
			if (sessionUser.getRole() == Role.GAMER) {
				ProfileGamer sessionUserProfile = (ProfileGamer) sessionUser.getProfile();
				List<User> friendList = sessionUserProfile.getFriends();
				boolean isFollowing = friendList.contains(profileUser);
				model.addAttribute("isFollowing", isFollowing);
			}
			return "profile-gamer";
		}
		
		if (profileUser.getRole() == Role.DEVELOPER) {
			if (sessionUser.getRole() == Role.GAMER) {
				ProfileGamer sessionUserProfile = (ProfileGamer) sessionUser.getProfile();
				List<User> followedDevList = sessionUserProfile.getFollowedDevelopers();
				boolean isFollowing = followedDevList.contains(profileUser);
				model.addAttribute("isFollowing", isFollowing);
			}
			model.addAttribute("gameList", gameService.findGamesByDevId(profileUser.getId()));
			return "profile-dev";
		}
		
		return "profile-error";
	}
	
	@GetMapping(value = "/edit")
	public String userProfileEditForm(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("userProfileForm", new FormUserProfile());
		
		return "profile-edit";
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
	
//	@PostMapping(value = "/activity")
//	public String viewActivity(Model model, HttpSession sessionObj) {
//		User user = (User) sessionObj.getAttribute("user");
//		
//		if (user.getRole() != Role.GAMER) {
//			return "redirect:/";
//		}
//		
//		List<Post> gamerPosts = postService.findPostsByUserId(user.getId());
//		model.addAttribute("gamerPosts", gamerPosts);
//		return "activity";
//	}
}
