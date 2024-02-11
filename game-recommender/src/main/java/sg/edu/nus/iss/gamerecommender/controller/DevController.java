package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@Controller
@RequestMapping("/dev")
public class DevController {

	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = {"", "/", "/dashboard"})
	public String devDashboard(Model model, HttpSession sessionObj) {
		User dev = (User) sessionObj.getAttribute("user");
		
		// Dashboard Counts
		Integer publishedGamesCount = gameService.countGamesByDevId(dev.getId());
		Double avgRating = gameService.getAverageGameRatingByDevId(dev.getId());
		Integer gameFollowers = userService.countGamesFollowersByDevId(dev.getId());
		Integer accountFollowers = userService.countAccountFollowersByDevId(dev.getId());
		model.addAttribute("publishedGamesCount", publishedGamesCount);
		model.addAttribute("avgRating", avgRating*100);
		model.addAttribute("gameFollowers", gameFollowers);
		model.addAttribute("accountFollowers", accountFollowers);
		
		// Dashboard Graphs
		Page<Game> topRatedGames = gameService.findTopRatedByDevId(dev.getId(), 1, 3);
		Page<Game> topFollowedGames = gameService.findTopFollowedByDevId(dev.getId(), 1, 3);
		List<IGenreCount> genreCount = gameService.countGameGenreDistributionByDevId(dev.getId());
		
		// Number of Followers by Day (Line, Week)
			    
	    return "dev-dashboard";
	}
	
	@GetMapping(value = {"/game-applications/pending"})
	public String devGamePagesPending() {
		
		// List pending game pages
		
	    return "admin-pending-list";
	}
		
	@GetMapping(value = "/games")
	public String devGameList(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		List<Game> gameList = gameService.findGamesByDevId(user.getId());
		model.addAttribute("gameList", gameList);
		return "dev-game-list";
	}
	
	@GetMapping(value = "/game-applications/list/all")
	public String devGameApplications(Model model, HttpSession sessionObj) {

		return "dev-game-app-list";
	}
	
	@GetMapping(value = "/game-applications/list/pending")
	public String devGameApplicationsPending(Model model, HttpSession sessionObj) {

		return "dev-game-app-pending";
	}
	
	@GetMapping(value = "/game-applications/view/{id}")
	public String gameApplicationView(Model model, HttpSession sessionObj) {
		return "dev-game-app-profile";
	}
	
	@GetMapping(value = "/game-applications/create")
	public String gameCreateForm(Model model, HttpSession sessionObj) {
		return "dev-game-app-create";
	}
	
	@GetMapping(value = "/game-applications/edit/{id}")
	public String gameEditForm(Model model, HttpSession sessionObj) {
		// Change Details
		return "dev-game-app-edit";
	}
		
	@PostMapping(value = "/game-applications/delete/{id}")
	public String gameDelete(Model model, HttpSession sessionObj) {
		return "redirect:/dev/game-applications/list/all";
	}
}
