package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.service.GameService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	GameService gameService;
	
	// Note: ApproverId must be recorded, along with Comments
	
	@GetMapping(value = {"", "/", "/dashboard"})
	public String adminDashboard(Model model, HttpSession sessionObj) {
		// Get Statistics
		// Get List of PENDING Game Pages (Show Max 5)
		// Get List of Ban Requests (Show Max 5)
		return "admin-dashboard";
	}
	
	@GetMapping(value = "/notifications")
	public String adminNotifications(Model model, HttpSession sessionObj) {
		// Show list of read/unread notifications
		// Allow user to mark as read/unread
		return "admin-notification-list";
	}

}
