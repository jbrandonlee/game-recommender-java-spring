package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.gamerecommender.dto.FormUserProfile;
import sg.edu.nus.iss.gamerecommender.model.Game;
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
		
		if (!isProfileOwner && !isProfileVisible) {
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
	
	@GetMapping(value = "/{id}/games")
	public String devProfileGames(@PathVariable("id") Integer devId, Model model,
			HttpSession sessionObj, HttpServletRequest request,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		User user = userService.findUserById(devId);
		if (user.getRole() != Role.DEVELOPER) {
			return "redirect:/user/profile";
		}
		
		int currPage = page.orElse(1);
		int pageSize = size.orElse(25);

		Page<Game> gameList = gameService.findGamesByDevId(devId, currPage, pageSize); 
		
		model.addAttribute("currUrl", request.getRequestURI().toString());
		model.addAttribute("currPage", currPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", gameList.getTotalPages());
		model.addAttribute("totalItems", gameList.getTotalElements());
		model.addAttribute("gameList", gameList);
		return "dev-game-list";
	}
}
