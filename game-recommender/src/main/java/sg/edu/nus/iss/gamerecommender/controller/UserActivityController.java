package sg.edu.nus.iss.gamerecommender.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.ActivityService;

@Controller
@RequestMapping(value = "/user/activity")
public class UserActivityController {
	
	@Autowired
	ActivityService activityService;
	
	@GetMapping(value = { "", "/" })
	public String activityFeed(Model model, HttpSession sessionObj, HttpServletRequest request,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		int currPage = page.orElse(1);
		int pageSize = size.orElse(25);
		
		User user = (User) sessionObj.getAttribute("user");
		Page<Activity> activityFeed = activityService.findUserActivity(user.getId(), currPage, pageSize);
		
		model.addAttribute("currUrl", request.getRequestURI().toString());
		model.addAttribute("currPage", currPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", activityFeed.getTotalPages());
		model.addAttribute("totalItems", activityFeed.getTotalElements());
		model.addAttribute("activityFeed", activityFeed);
		return "activity-feed";
	}
}
