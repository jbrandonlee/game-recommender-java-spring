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
import sg.edu.nus.iss.gamerecommender.dto.FormDevProfile;
import sg.edu.nus.iss.gamerecommender.dto.FormGamerProfile;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
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
	
	@GetMapping(value = "/edit-gamer")
	public String gamerProfileEditForm(Model model, HttpSession sessionObj) {
		User user = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		model.addAttribute("user", user);
		model.addAttribute("userProfileForm", new FormDevProfile());
		model.addAttribute("genreList", Genre.values());
		return "profile-edit-gamer";
	}
	
	@PostMapping(value = "/edit-gamer")
	public String updateGamerProfile(@Valid @ModelAttribute("user") FormGamerProfile userProfileForm,
			BindingResult bindingResult, Model model, HttpSession sessionObj) {

		User user = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		user.setDisplayName(userProfileForm.getDisplayName());
		user.setDisplayImageUrl(userProfileForm.getDisplayImageUrl());
		user.setBiography(userProfileForm.getBiography());
		user.getProfile().setVisibilityStatus(userProfileForm.isVisibilityStatus());
		((ProfileGamer) user.getProfile()).setGenrePreferences(userProfileForm.getGenrePreferences());
		
		userService.updateUser(user);
		
		return "redirect:/user/profile";
	}
	
	@GetMapping(value = "/edit-dev")
	public String devProfileEditForm(Model model, HttpSession sessionObj) {
		User user = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		model.addAttribute("user", user);
		model.addAttribute("userProfileForm", new FormDevProfile());
		
		return "profile-edit-dev";
	}
	
	@PostMapping(value = "/edit-dev")
	public String devUserProfile(@Valid @ModelAttribute("user") FormDevProfile userProfileForm,
			BindingResult bindingResult, Model model, HttpSession sessionObj) {

		User user = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		user.setDisplayName(userProfileForm.getDisplayName());
		user.setDisplayImageUrl(userProfileForm.getDisplayImageUrl());
		user.setBiography(userProfileForm.getBiography());
		user.getProfile().setVisibilityStatus(userProfileForm.isVisibilityStatus());
		
		userService.updateUser(user);
		
		return "redirect:/user/profile";
	}
	
	// --------------------
	// -- View All Lists --
	// --------------------
	@GetMapping(value = "/{id}/games")
	public String devProfileGames(@PathVariable("id") Integer devId, Model model,
			HttpSession sessionObj, HttpServletRequest request,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		User dev = userService.findUserById(devId);
		if (dev.getRole() != Role.DEVELOPER) {
			return "redirect:/user/profile" + devId;
		}
		
		int currPage = page.orElse(1);
		int pageSize = size.orElse(10);

		Page<Game> gameList = gameService.findGamesByDevId(devId, currPage, pageSize); 
		
		model.addAttribute("currUrl", request.getRequestURI().toString());
		model.addAttribute("currPage", currPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", gameList.getTotalPages());
		model.addAttribute("totalItems", gameList.getTotalElements());
		model.addAttribute("gameList", gameList);
		model.addAttribute("dev", dev);
		return "dev-game-list";
	}
	
	@GetMapping(value = "/{id}/followed_games")
	public String userFollowedGames(@PathVariable("id") Integer userId, Model model, HttpSession sessionObj) {
		User user = userService.findUserById(userId);
		if (user.getRole() != Role.GAMER) {
			return "redirect:/user/profile" + userId;
		}
		
		ProfileGamer profile = (ProfileGamer) user.getProfile();
		List<Game> gameList = profile.getFollowedGames();
		model.addAttribute("user", user);
		model.addAttribute("gameList", gameList);
		return "profile-gamer-game-list";
	}
	
	@GetMapping(value = "/{id}/followed_developers")
	public String userFollowedDevs(@PathVariable("id") Integer userId, Model model, HttpSession sessionObj) {
		User user = userService.findUserById(userId);
		if (user.getRole() != Role.GAMER) {
			return "redirect:/user/profile" + userId;
		}
		
		ProfileGamer profile = (ProfileGamer) user.getProfile();
		List<User> userList = profile.getFollowedDevelopers();
		model.addAttribute("user", user);
		model.addAttribute("userList", userList);
		return "profile-gamer-dev-list";
	}
	
	@GetMapping(value = "/{id}/followed_gamers")
	public String userFollowedGamers(@PathVariable("id") Integer userId, Model model, HttpSession sessionObj) {
		User user = userService.findUserById(userId);
		if (user.getRole() != Role.GAMER) {
			return "redirect:/user/profile" + userId;
		}
		
		ProfileGamer profile = (ProfileGamer) user.getProfile();
		List<User> userList = profile.getFriends();
		model.addAttribute("user", user);
		model.addAttribute("userList", userList);
		return "profile-gamer-friend-list";
	}
}
