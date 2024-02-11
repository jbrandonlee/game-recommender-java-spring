package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.UserService;
import sg.edu.nus.iss.gamerecommender.util.DashboardUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = {"", "/", "/dashboard"})
	public String adminDashboard(Model model, HttpSession sessionObj) {
		// Dashboard Counts
		// TODO: Pending Game Page Applications
		Integer gamerCount = userService.countAllUsersbyRole(Role.GAMER);
		Integer devCount = userService.countAllUsersbyRole(Role.DEVELOPER);
		Integer gameCount = gameService.countAllGames();
		model.addAttribute("gamerCount", gamerCount);
		model.addAttribute("devCount", devCount);
		model.addAttribute("gameCount", gameCount);
		
		// Dashboard Graphs
		Page<Game> topRatedGames = gameService.findTopRated(1, 5);
		Page<Game> topFollowedGames = gameService.findTopFollowed(1, 5);
		List<IGenreCount> genreCount = userService.countUserGenrePrefs();
		List<String> pastWeekDayNames = DashboardUtil.getPastWeekDayNamesFromNow();
		List<Integer> pastWeekNewGamers = userService.countPastWeekNewUsersByRole(Role.GAMER);
		List<Integer> pastWeekNewDevs = userService.countPastWeekNewUsersByRole(Role.DEVELOPER);
		List<Integer> pastWeekNewGames = gameService.countPastWeekNewGamePages();
		
	    return "admin-dashboard";
	}
	
	@GetMapping(value = {"/game-application/pending"})
	public String adminPending() {
		
		// List all pending game pages to approve
		
	    return "admin-pending-list";
	}
	
}
