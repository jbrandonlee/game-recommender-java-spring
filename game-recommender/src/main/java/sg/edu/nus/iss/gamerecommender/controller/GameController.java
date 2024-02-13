package sg.edu.nus.iss.gamerecommender.controller;

import java.time.LocalDate;
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
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.PostGame;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.PostService;
import sg.edu.nus.iss.gamerecommender.service.RecommenderService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@Controller
@RequestMapping(value = "/game")
public class GameController {

	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	RecommenderService reccService;
	
	// Show Game Page
	@GetMapping(value = "/{id}")
	public String gameProfile(@PathVariable("id") Integer gameId, Model model, HttpSession sessionObj) {
		User user = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		Game game = gameService.findGameById(gameId);
		
		boolean isProfileOwner = false;
		if (user.getRole() == Role.DEVELOPER) {
			List<Game> developedGameList = gameService.findGamesByDevId(user.getId());
			isProfileOwner = developedGameList.contains(game);
		}
		model.addAttribute("isProfileOwner", isProfileOwner);

		boolean isProfileVisible = game.getProfile().isVisibilityStatus();
		if (!isProfileOwner && !isProfileVisible) {
			return "profile-hidden";
		}
		
		if (user.getRole() == Role.GAMER) {
			ProfileGamer sessionUserProfile = (ProfileGamer) user.getProfile();
			List<Game> followedGameList = sessionUserProfile.getFollowedGames();
			boolean isFollowing = followedGameList.contains(game);
			model.addAttribute("isFollowing", isFollowing);
		}
		
		Page<PostGame> gameUpdatePosts = postService.findUpdatePostsByGameIdDesc(gameId, 1, 2);
		model.addAttribute("gameUpdatePosts", gameUpdatePosts);
		
		PostGameReview gameReviewPost = postService.findReviewPostByGameAndUserId(user.getId(), game.getId());
		if (gameReviewPost == null) { 
			gameReviewPost = new PostGameReview();
		}
		model.addAttribute("gameReviewPost", gameReviewPost);
		
		//String idList = reccService.getRelatedGameIds(gameId);
		//model.addAttribute("recommendations", reccommendations);
		model.addAttribute("game", game);
		return "profile-game";
	}
	
	// Show Game Update List
	@GetMapping(value = "/{id}/update")
	public String gameUpdates(@PathVariable("id") Integer gameId, Model model,
			HttpSession sessionObj, HttpServletRequest request,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		// Check if Profile Owner
		User user = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		Game game = gameService.findGameById(gameId);
		
		boolean isProfileOwner = false;
		if (user.getRole() == Role.DEVELOPER) {
			List<Game> developedGameList = gameService.findGamesByDevId(user.getId());
			isProfileOwner = developedGameList.contains(game);
		}
		model.addAttribute("isProfileOwner", isProfileOwner);

		// Handle List
		int currPage = page.orElse(1);
		int pageSize = size.orElse(10);
		Page<PostGame> gameUpdatePosts = postService.findUpdatePostsByGameIdDesc(gameId, currPage, pageSize);
		
		model.addAttribute("currUrl", request.getRequestURI().toString());
		model.addAttribute("currPage", currPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", gameUpdatePosts.getTotalPages());
		model.addAttribute("totalItems", gameUpdatePosts.getTotalElements());
		model.addAttribute("gameUpdatePosts", gameUpdatePosts);
		model.addAttribute("isProfileOwner", isProfileOwner);
		return "game-update-list";
	}
	
	// User Post Review
	@PostMapping(value = "/{id}/create-review")
	public String createGameReviewPost(@PathVariable("id") Integer gameId,
			@Valid @ModelAttribute("gameReviewForm") PostGameReview gameReviewForm,
			BindingResult bindingResult, Model model, HttpSession sessionObj) {
		
		User user = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		Game game = gameService.findGameById(gameId);
		PostGameReview gameReviewPost = postService.findReviewPostByGameAndUserId(user.getId(), game.getId());
		
		if (gameReviewPost == null) {
			gameReviewPost = new PostGameReview();
		}
		
		gameReviewPost.setUserProfile(user.getProfile());
		gameReviewPost.setGameProfile(game.getProfile());
		gameReviewPost.setMessage(gameReviewForm.getMessage());
		gameReviewPost.setIsRecommend(gameReviewForm.getIsRecommend());
		gameReviewPost.setDatePosted(LocalDate.now());
		
		postService.createPostGameReview(gameReviewPost, user.getId(), game.getId());
		return "redirect:/game/" + gameId;
	}
	
	// Dev Toggle Visibility
	@PostMapping(value = "/{id}/toggle-visibility")
	public String toggleGameVisibility(@PathVariable("id") Integer gameId,
			Model model, HttpSession sessionObj) {
		
		User dev = (User) sessionObj.getAttribute("user");
		if (dev.getRole() != Role.DEVELOPER) {
			return "redirect:/game/" + gameId;
		}
		
		List<Integer> findGameIdsByDevId = gameService.findGameIdsByDevId(dev.getId());
		if (findGameIdsByDevId.contains(gameId)) {
			Game game = gameService.findGameById(gameId);
			ProfileGame gameProfile = game.getProfile();
			gameProfile.setVisibilityStatus(!gameProfile.isVisibilityStatus());
			gameService.updateGame(game);
		}
		
		return "redirect:/game/" + gameId;
	}
	
	// --------------------------
	// -- Dev Game Update CRUD --
	// --------------------------

}
