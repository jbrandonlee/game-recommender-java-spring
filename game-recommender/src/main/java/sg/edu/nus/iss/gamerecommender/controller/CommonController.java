package sg.edu.nus.iss.gamerecommender.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.service.GameService;

@Controller
public class CommonController {
	
	@Autowired
	GameService gameService;
	
	// TODO: Add Pagination
	// TODO: Add Filter Options (e.g. Genre)
	@GetMapping(value = "/search") 
	public String search(@RequestParam("type") Optional<String> type, 
			@RequestParam("query") Optional<String> query,
			Model model, HttpSession sessionObj) {
		
		// If user navigates to page, no Query String, show all Games
		if (type.isEmpty() && query.isEmpty()) {
			List<Game> gameList = gameService.findAllSortedTopRating();
			model.addAttribute("gameList", gameList);
			return "search";
		}
		
		String searchType = type.orElse("game");
		String searchQuery = query.orElse("");

		// If user puts empty search, show no items found
		if (searchQuery.isBlank()) {
			model.addAttribute("searchType", searchType);
			model.addAttribute("gameList", new ArrayList<Game>());
			return "search";
		}
		
		// TODO: Else find items by type
		if (searchType == "game") {
			
		} else if (searchType == "dev") {
			
		} else if (searchType == "user") {
			
		}
			
		return "search";
	}
	
	// TODO: View Notifications
	@GetMapping(value = "/notifications/all")
	public String notificationList(Model model, HttpSession sessionObj) {
		
		return "notification-list";
	}
}
