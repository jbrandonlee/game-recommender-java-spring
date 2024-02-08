package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.RecommenderService;

@Controller
@RequestMapping(value = "/game")
public class GameController {

	@Autowired
	GameService gameService;
	
	@Autowired
	RecommenderService reccService;
	
	@GetMapping(value = "/{id}")
	public String gameProfile(@PathVariable("id") Integer gameId, Model model, HttpSession sessionObj) {
		Game game = gameService.findGameById(gameId);
		boolean isProfileVisible = game.getProfile().isVisibilityStatus();
		//String idList = reccService.getRelatedGameIds(gameId);
		
		if (!isProfileVisible) {
			return "profile-hidden";
		}
		
		model.addAttribute("game", game);
		//model.addAttribute("recommendations", reccommendations);
		return "profile-game";
	}
	
}
