package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.GameService;

@Controller
@RequestMapping("/dev/game_application")
public class GameApplicationController {

	@Autowired
	GameService gameService;
	
	@GetMapping(value = "/list")
	public String devGameList(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		List<Game> gameList = gameService.findGamesByDevId(user.getId());
		model.addAttribute("gameList", gameList);
		return "dev-game-list";
	}
	
	@GetMapping(value = "/view/{id}")
	public String gameProfileView(Model model, HttpSession sessionObj) {
		return "dev-game-profile";
	}
	
	@GetMapping(value = "/create")
	public String gameCreateForm(Model model, HttpSession sessionObj) {
		return "dev-game-create";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String gameEditForm(Model model, HttpSession sessionObj) {
		// Change Visibility or Details
		return "dev-game-edit";
	}
		
	@PostMapping(value = "/delete/{id}")
	public String gameDelete(Model model, HttpSession sessionObj) {
		return "redirect:/dev/game/list";
	}
	
	
}
