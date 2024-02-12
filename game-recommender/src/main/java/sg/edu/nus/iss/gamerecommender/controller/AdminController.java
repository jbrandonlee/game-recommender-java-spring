package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping(value = {"", "/", "/dashboard"})
	public String adminDashboard() {
		
		// Top 5 Games (Rating) (Bar)
		
		// Top 5 Games (Total Followers) (Bar)
		
		// Top User Genre Preferences (Pie)
		
		// New Users, Games, Developers by Day (Line, Week)
		
		// Get List of PENDING Game Pages (Show Max 5)
		
	    return "admin-dashboard";
	}
	
	@GetMapping(value = {"/game-application/pending"})
	public String adminPending() {
		
		// List all pending game pages to approve
		
	    return "admin-pending-list";
	}
	
}
