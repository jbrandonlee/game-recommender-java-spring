package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
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
	public String adminDashboard(Model model, HttpSession sessionObj) throws JsonProcessingException {
		// Dashboard Counts
		// TODO: Pending Game Page Applications
		Integer gamerCount = userService.countAllUsersbyRole(Role.GAMER);
		Integer devCount = userService.countAllUsersbyRole(Role.DEVELOPER);
		Integer gameCount = gameService.countAllGames();
		model.addAttribute("gamerCount", gamerCount);
		model.addAttribute("devCount", devCount);
		model.addAttribute("gameCount", gameCount);
		
		// Dashboard Graphs
		// -- Convert to JSON String, Pass to Hidden Input Field --
		JsonObject data = new JsonObject();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		// Graph 1 - Top Rated Games
		Page<String> topRatedGameTitles = gameService.findTopRatedTitles(1, 5);
		Page<Double> topRatedGameRatings = gameService.findTopRatedRatings(1, 5);
		data.addProperty("topRatedGameTitles", objectMapper.writeValueAsString(topRatedGameTitles));
		data.addProperty("topRatedGameRatings", objectMapper.writeValueAsString(topRatedGameRatings));
		
		// Graph 2 - Top Followed Games
		Page<String> topFollowedGameTitles = gameService.findTopFollowedTitles(1, 5);
		Page<Integer> topFollowedFollowerCount = gameService.countTopFollowedTitlesFollowers(1, 5);
		data.addProperty("topFollowedGameTitles", objectMapper.writeValueAsString(topFollowedGameTitles));
		data.addProperty("topFollowedFollowerCount", objectMapper.writeValueAsString(topFollowedFollowerCount));
		
		// Graph 3 - User Genre Preferences
		List<IGenreCount> genreCount = userService.countUserGenrePrefs();
		data.addProperty("genreCount", objectMapper.writeValueAsString(genreCount));
		
		// Graph 4 - New Users / Games
		List<String> pastWeekDayNames = DashboardUtil.getPastWeekDayNamesFromNow();
		List<Integer> pastWeekNewGamers = userService.countPastWeekNewUsersByRole(Role.GAMER);
		List<Integer> pastWeekNewDevs = userService.countPastWeekNewUsersByRole(Role.DEVELOPER);
		List<Integer> pastWeekNewGames = gameService.countPastWeekNewGamePages();
		data.addProperty("pastWeekDayNames", objectMapper.writeValueAsString(pastWeekDayNames));
		data.addProperty("pastWeekNewGamers", objectMapper.writeValueAsString(pastWeekNewGamers));
		data.addProperty("pastWeekNewDevs", objectMapper.writeValueAsString(pastWeekNewDevs));
		data.addProperty("pastWeekNewGames", objectMapper.writeValueAsString(pastWeekNewGames));
		
		model.addAttribute("jsonData", data);
	    return "admin-dashboard";
	}
	
	@GetMapping(value = {"/game-application/pending"})
	public String adminPending() {
		
		// List all pending game pages to approve
		
	    return "admin-pending-list";
	}
	
}
