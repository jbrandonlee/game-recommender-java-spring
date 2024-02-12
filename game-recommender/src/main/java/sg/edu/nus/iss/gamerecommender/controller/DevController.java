package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.ActivityService;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.UserService;
import sg.edu.nus.iss.gamerecommender.util.DashboardUtil;

@Controller
@RequestMapping("/dev")
public class DevController {

	@Autowired
	ActivityService activityService;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = { "/profile" })
	public String devProfile(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		return "redirect:/user/profile/" + user.getId();
	}
	
	@GetMapping(value = {"", "/", "/dashboard"})
	public String devDashboard(Model model, HttpSession sessionObj) throws JsonProcessingException {
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
		// -- Convert to JSON String, Pass to Hidden Input Field --
		JsonObject data = new JsonObject();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		// Graph 1 - Top Rated Games
		Page<String> topRatedGameTitles = gameService.findTopRatedTitlesByDevId(dev.getId(), 1, 3);
		Page<Double> topRatedGameRatings = gameService.findTopRatedRatingsByDevId(dev.getId(), 1, 3);
		data.addProperty("topRatedGameTitles", objectMapper.writeValueAsString(topRatedGameTitles));
		data.addProperty("topRatedGameRatings", objectMapper.writeValueAsString(topRatedGameRatings));
		
		// Graph 2 - Top Followed Games
		Page<String> topFollowedGameTitles = gameService.findTopFollowedTitlesByDevId(dev.getId(), 1, 3);
		Page<Integer> topFollowedFollowerCount = gameService.countTopFollowedTitlesFollowersByDevId(dev.getId(), 1, 3);
		data.addProperty("topFollowedGameTitles", objectMapper.writeValueAsString(topFollowedGameTitles));
		data.addProperty("topFollowedFollowerCount", objectMapper.writeValueAsString(topFollowedFollowerCount));
		
		// Graph 3 - User Genre Preferences
		List<IGenreCount> genreCount = gameService.countGameGenreDistributionByDevId(dev.getId());
		data.addProperty("genreCount", objectMapper.writeValueAsString(genreCount));
		
		// Graph 4 - New Users / Games
		List<String> pastWeekDayNames = DashboardUtil.getPastWeekDayNamesFromNow();
		List<Integer> pastWeekNewAccFollows = activityService.countPastWeekNewAccountFollowersByDevId(dev.getId());
		List<Integer> pastWeekNewGameFollows = activityService.countPastWeekNewGameFollowersByDevId(dev.getId());
		data.addProperty("pastWeekDayNames", objectMapper.writeValueAsString(pastWeekDayNames));
		data.addProperty("pastWeekNewAccFollows", objectMapper.writeValueAsString(pastWeekNewAccFollows));
		data.addProperty("pastWeekNewGameFollows", objectMapper.writeValueAsString(pastWeekNewGameFollows));
		
		model.addAttribute("jsonData", data);
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
