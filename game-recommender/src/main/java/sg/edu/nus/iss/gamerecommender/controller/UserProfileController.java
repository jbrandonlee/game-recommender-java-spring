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
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;

@Controller
@RequestMapping(value = "/user/profile")
public class UserProfileController {
	
	@Autowired
	GameRepository gameRepo;
	
	@GetMapping(value = { "", "/" })
	public String userProfile(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		model.addAttribute("user", user);
		
		if (user.getRole() == Role.DEVELOPER) {
			List<Game> gameList = gameRepo.findGamesByDevId(user.getId());
			model.addAttribute("gameList", gameList);
		}
		
		return "account-profile";
	}
	
	@GetMapping(value = "/edit")
	public String userProfileEditForm(Model model, HttpSession sessionObj) {
		return "account-profile-edit";
	}
	
	@PostMapping(value = "/edit")
	public String updateUserProfile(Model model, HttpSession sessionObj) {
		return "account-profile";
	}
}
