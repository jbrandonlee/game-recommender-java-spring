package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.service.GameService;

@Controller
@RequestMapping("/dev")
public class DevController {

	@Autowired
	GameService gameService;
	
	@GetMapping(value = {"", "/", "/dashboard"})
	public String devDashboard(Model model, HttpSession sessionObj) {
		// Get Statistics
		// Get List of Next Actions
		// Get List of Games by Dev (Show Max 5)
		return "dev-dashboard";
	}
	
	@GetMapping(value = "/profile")
	public String devProfile(Model model, HttpSession sessionObj) {
		return "dev-profile";
	}
	
	@GetMapping(value = "/profile/edit")
	public String devProfileEdit(Model model, HttpSession sessionObj) {
		return "dev-profile-edit";
	}
	
	@GetMapping(value = "/notifications")
	public String devNotifications(Model model, HttpSession sessionObj) {
		// Show list of read/unread notifications
		// Allow user to mark as read/unread
		return "dev-notification-list";
	}
	
}
