package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.GameService;
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
	RecommenderService reccService;
	
	@GetMapping(value = "/{id}")
	public String gameProfile(@PathVariable("id") Integer gameId, Model model, HttpSession sessionObj) {
		User sessionUser = userService.findUserById(((User) sessionObj.getAttribute("user")).getId());
		Game game = gameService.findGameById(gameId);
		
		boolean isProfileVisible = game.getProfile().isVisibilityStatus();
		if (!isProfileVisible) {
			return "profile-hidden";
		}
		
		if (sessionUser.getRole() == Role.GAMER) {
			ProfileGamer sessionUserProfile = (ProfileGamer) sessionUser.getProfile();
			List<Game> followedGameList = sessionUserProfile.getFollowedGames();
			boolean isFollowing = followedGameList.contains(game);
			model.addAttribute("isFollowing", isFollowing);
		}
		
		//String idList = reccService.getRelatedGameIds(gameId);
		//model.addAttribute("recommendations", reccommendations);
		model.addAttribute("game", game);
		return "profile-game";
	}
	
}
