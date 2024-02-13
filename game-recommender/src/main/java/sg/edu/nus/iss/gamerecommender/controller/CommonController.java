package sg.edu.nus.iss.gamerecommender.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.UserService;
import sg.edu.nus.iss.gamerecommender.util.PageUtil;

@Controller
public class CommonController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	 
	// TODO: Add Filter Options (e.g. Genre)
	@GetMapping(value = "/search") 
	public String search(Model model, HttpSession sessionObj, HttpServletRequest request,
			@RequestParam("type") Optional<String> type, 
			@RequestParam("query") Optional<String> query,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		String searchType = type.orElse("game");
		String searchQuery = query.orElse("");
		int currPage = page.orElse(1);
		int pageSize = size.orElse(10);
		
		// Handle game search
		if (searchType.equals("game")) {
			List<Game> gameList;
			
			// New page visit, no Query String, show all Games
			if (type.isEmpty() && query.isEmpty()) {
				gameList = gameService.findAllSortedTopRating();
			} else {
				gameList = gameService.searchGames(searchQuery);
			}
			
			int maxPage = (int) Math.ceil(gameList.size() / (double) pageSize);
			int getPageNum = Math.max(1, Math.min(maxPage, currPage)) - 1;
			Page<Game> pageItem = PageUtil.getPage(getPageNum, pageSize, gameList);
			List<Game> pagedItemList = pageItem.getContent();
			
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchQuery", searchQuery);
			model.addAttribute("searchList", pagedItemList);
			model.addAttribute("currUrl", request.getRequestURI().toString());
			model.addAttribute("currPage", currPage);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", pageItem.getTotalPages());
			model.addAttribute("totalItems", pageItem.getTotalElements());
			return "search";
		}
		
		// Handle user search
		if (searchType.equals("dev") || searchType.equals("user")) {
			List<User> userList;
			
			if (searchType.equals("dev")) {
				userList = userService.searchDevelopers(searchQuery);
			} else {
				userList = userService.searchGamers(searchQuery);
			}
			
			int maxPage = (int) Math.ceil(userList.size() / (double) pageSize);
			int getPageNum = Math.max(1, Math.min(maxPage, currPage)) - 1;
			Page<User> pageItem = PageUtil.getPage(getPageNum, pageSize, userList);
			List<User> pagedItemList = pageItem.getContent();
			
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchQuery", searchQuery);
			model.addAttribute("searchList", pagedItemList);
			model.addAttribute("currUrl", request.getRequestURI().toString());
			model.addAttribute("currPage", currPage);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", pageItem.getTotalPages());
			model.addAttribute("totalItems", pageItem.getTotalElements());
			return "search";
		}
						
		model.addAttribute("searchList", new ArrayList<>());
		return "search";
	}

}
