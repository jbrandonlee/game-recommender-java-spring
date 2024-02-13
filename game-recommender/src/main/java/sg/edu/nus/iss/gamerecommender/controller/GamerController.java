package sg.edu.nus.iss.gamerecommender.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.ActivityService;
import sg.edu.nus.iss.gamerecommender.service.GameService;

@Controller
@RequestMapping("/user")
public class GamerController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	ActivityService activityService;
	
	@GetMapping(value = {"", "/", "/home"})
	public String gamerDashboard(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		
		Page<Game> topRatedGames = gameService.findTopRated(1, 4);
		Page<Game> topFollowedGames = gameService.findTopFollowed(1, 4);
//		List<Game> topFollowedGames = topFollowedGames.getContent();
		// Recommender Games (ML)
		
		model.addAttribute("topRatedGames", topRatedGames);
		model.addAttribute("topFollowedGames", topFollowedGames);
		//model.addAttribute("userRecommendedGames", userRecommendedGames);
	    return "gamer-dashboard";
	}
	
	@GetMapping(value = { "/activity" })
	public String activityFeed(Model model, HttpSession sessionObj, HttpServletRequest request,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		int currPage = page.orElse(1);
		int pageSize = size.orElse(25);
		
		User user = (User) sessionObj.getAttribute("user");
		Page<Activity> activityFeed = activityService.findUserActivity(user.getId(), currPage, pageSize);
		
		model.addAttribute("currUrl", request.getRequestURI().toString());
		model.addAttribute("currPage", currPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", activityFeed.getTotalPages());
		model.addAttribute("totalItems", activityFeed.getTotalElements());
		model.addAttribute("activityFeed", activityFeed);
		return "activity-feed";
	}
	
}
