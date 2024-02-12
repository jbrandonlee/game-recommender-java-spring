package sg.edu.nus.iss.gamerecommender.controller;

import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.Game.Platform;
import sg.edu.nus.iss.gamerecommender.model.GameApplication;
import sg.edu.nus.iss.gamerecommender.model.GameApplication.ApprovalStatus;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.ActivityService;
import sg.edu.nus.iss.gamerecommender.service.GameApplicationService;
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
	GameApplicationService gameApplicationService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = { "/profile" })
	public String devProfile(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		return "redirect:/user/profile/" + user.getId();
	}
	
	@GetMapping(value = "/games")
	public String devGameList(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		List<Game> gameList = gameService.findGamesByDevId(user.getId());
		model.addAttribute("gameList", gameList);
		return "redirect:/user/profile/" + user.getId() + "/games";		// TODO
//		return "dev-game-list";
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
	
	// ----------------------------
	// -- Game Applications CRUD --
	// ----------------------------
	@GetMapping(value = "/game-application")
	public String devGameApplicationsPending(Model model, HttpSession sessionObj,
			@RequestParam("showAll") Optional<Boolean> showAll) {

		User dev = (User) sessionObj.getAttribute("user");		
		Boolean isShowAll = showAll.orElse(false);
		List<GameApplication> gameApplicationList = new ArrayList<>();
		
		if (isShowAll) {
			gameApplicationList = gameApplicationService.findAllByDevId(dev.getId());
		} else {
			gameApplicationList = gameApplicationService.findPendingByDevId(dev.getId());
		}
		
		model.addAttribute("showAll", isShowAll);
		model.addAttribute("gameApplicationList", gameApplicationList);
		return "game-app-list";
	}
	
	@GetMapping(value = "/game-application/create")
	public String gameApplicationCreateForm(Model model, HttpSession sessionObj) {
		GameApplication gameApplication = new GameApplication();
		model.addAttribute("platformList", Platform.values());
		model.addAttribute("genreList", Genre.values());
		model.addAttribute("gameApplication", gameApplication);
		return "game-app-create";
	}
	
	@PostMapping(value = "/game-application/create")
	public String createGameApplication(Model model, HttpSession sessionObj,
			@Valid @ModelAttribute("gameApplication") GameApplication gameApplicationForm, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("platformList", Platform.values());
			model.addAttribute("genreList", Genre.values());
			return "game-app-create";
		}
		
		User dev = (User) sessionObj.getAttribute("user");		
		gameApplicationForm.setDeveloper(dev);
		gameApplicationForm.setApprovalStatus(ApprovalStatus.APPLIED);
		gameApplicationService.createGameApplication(gameApplicationForm);

		return "redirect:/dev/game-application";
	}
	
	@GetMapping(value = "/game-application/view/{id}")
	public String gameApplicationView(@PathVariable("id") Integer gameAppId, Model model, HttpSession sessionObj) {
		GameApplication gameApplication = gameApplicationService.findById(gameAppId);
		model.addAttribute("gameApplication", gameApplication);
		return "game-app-view";
	}
	
	@GetMapping(value = "/game-application/edit/{id}")
	public String gameApplicationEditForm(@PathVariable("id") Integer gameAppId, Model model, HttpSession sessionObj) {
		GameApplication gameApplication = gameApplicationService.findById(gameAppId);
		model.addAttribute("platformList", Platform.values());
		model.addAttribute("genreList", Genre.values());
		model.addAttribute("gameApplication", gameApplication);		
		return "game-app-edit";
	}
	
	@PostMapping(value = "/game-application/edit/{id}")
	public String editGameApplication(@PathVariable("id") Integer gameAppId, Model model, HttpSession sessionObj,
			@Valid @ModelAttribute("gameApplication") GameApplication gameApplicationForm, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("platformList", Platform.values());
			model.addAttribute("genreList", Genre.values());
			return "game-app-edit";
		}
		
		GameApplication gameApplication = gameApplicationService.findById(gameAppId);
		gameApplication.setTitle(gameApplicationForm.getTitle());
		gameApplication.setDescription(gameApplicationForm.getDescription());
		gameApplication.setDateRelease(gameApplicationForm.getDateRelease());
		gameApplication.setPrice(gameApplicationForm.getPrice());
		gameApplication.setImageUrl(gameApplicationForm.getImageUrl());
		gameApplication.setWebUrl(gameApplicationForm.getWebUrl());
		gameApplication.setPlatforms(gameApplicationForm.getPlatforms());
		gameApplication.setGenres(gameApplicationForm.getGenres());
		gameApplication.setApprovalStatus(ApprovalStatus.UPDATED);
		
		gameApplicationService.updateGameApplication(gameApplication);
		return "redirect:/dev/game-application";
	}
		
	@PostMapping(value = "/game-application/delete/{id}")
	public String deleteGameApplication(@PathVariable("id") Integer gameAppId, Model model, HttpSession sessionObj) {
		GameApplication gameApplication = gameApplicationService.findById(gameAppId);
		gameApplication.setApprovalStatus(ApprovalStatus.DELETED);
		gameApplicationService.updateGameApplication(gameApplication);
		return "redirect:/dev/game-application";
	}
	
	@PostMapping(value = "/game-application/publish/{id}")
	public String publishGamePage(Model model, HttpSession sessionObj) {
		return "redirect:/dev/games";
	}
}
