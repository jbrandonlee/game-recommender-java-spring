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
import sg.edu.nus.iss.gamerecommender.service.UserService;

@Controller
public class CommonController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	// TODO: Add Pagination
	// TODO: Make Cards Clickable
	// TODO: Add Filter Options (e.g. Genre)
	@GetMapping(value = "/search") 
	public String search(@RequestParam("type") Optional<String> type, 
			@RequestParam("query") Optional<String> query,
			Model model, HttpSession sessionObj) {
		
		// If user navigates to page, no Query String, show all Games
		if (type.isEmpty() && query.isEmpty()) {
			List<Game> gameList = gameService.findAllSortedTopRating();
			model.addAttribute("searchType", "game");
			model.addAttribute("searchList", gameList);
			return "search";
		}
		
		String searchType = type.orElse("game");
		String searchQuery = query.orElse("");
		model.addAttribute("searchType", searchType);
				
		if (searchQuery.isBlank()) {
			model.addAttribute("searchList", new ArrayList<>());
		} else if (searchType.equals("game")) {
			model.addAttribute("searchList", gameService.searchGames(searchQuery));
		} else if (searchType.equals("dev")) {
			model.addAttribute("searchList", userService.searchDevelopers(searchQuery));
		} else if (searchType.equals("user")) {
			model.addAttribute("searchList", userService.searchGamers(searchQuery));
		}
		return "search";
	}
	
	// TODO: View Notifications
	@GetMapping(value = "/notifications/all")
	public String notificationList(Model model, HttpSession sessionObj) {
		
		return "notification-list";
	}
}
