package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.AccountService;
import sg.edu.nus.iss.gamerecommender.service.DashboardService;
import sg.edu.nus.iss.gamerecommender.service.GameService;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {


	@Autowired
	private DashboardService dashService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private GameService gameService;

	@GetMapping(value = { "/admin_home", "/admin-dashboard" })
	public String admin_home(HttpSession sessionObj, Model model) {
		User user=(User) sessionObj.getAttribute("user");
		String username = user.getDisplayName();
		if (username == null) {
			return "redirect:/login";
		}
		long getTotalGamers = dashService.getTotalGamers();
		long getTotalDevelopers = dashService.getTotalDevelopers();
		long getTotalGames = dashService.getTotalGames();
		long getGamesPendingReview = dashService.getGamesPendingReview();
//		long getUserReports = dashService.getUserReports();

		model.addAttribute("username", username);

		model.addAttribute("totalGamers", getTotalGamers);
		model.addAttribute("totalDevelopers", getTotalDevelopers);
		model.addAttribute("totalGames", getTotalGames);
		model.addAttribute("gamesPendingReview", getGamesPendingReview);
//		model.addAttribute("userReports", getUserReports);

		return "admin_home";
	}


	@GetMapping(value = { "/game-list" })
	public String admin_game_gameList(HttpSession sessionObj, Model model) {
		List<Game> games = gameService.findAllGames();
		List game_list = new ArrayList();
		for (Game game : games) {
			System.out.println(game);
		}
		model.addAttribute("games", games);
		return "admin_gamelist";
	}

	@GetMapping("/game-list-search")
	public String admin_searchGame(@RequestParam("query") String query, Model model) {
		List<Game> searchResults = gameService.searchGamesByTerm(query);
		model.addAttribute("games", searchResults);
		return "admin_gamelist";
	}

	@GetMapping(value = { "/games-pending-review" })
	public String games_pending_review(HttpSession sessionObj, Model model) {
//		List<Game> games = gameService.findByGameStatus(Game.GameStatus.PENDING);
		List<Game> games = gameService.findAllGames();
		List game_list = new ArrayList();
		for (Game game : games) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("game", game);
			game_list.add(map);
		}
		model.addAttribute("games", game_list);
		return "admin_games_pending_review_list";
	}

	@GetMapping("/games-pending-review-detail")
	public String games_pending_review_detail(@RequestParam("id") Integer Id, Model model) {
		Optional<Game> game = gameService.findGameById(Id);
		Game gg = game.get();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("game", gg);
		model.addAttribute("game", map);
		return "admin_games_pending_review";
	}

	/*
	 * @GetMapping("/search") public String searchGames(@RequestParam("query")
	 * String query, Model model) { List<Game> games =
	 * gameService.searchGames(query); model.addAttribute("games", games); return
	 * "admin_gamelist"; }
	 */



	@GetMapping("/game-detail")
	public String viewGameDetail(@RequestParam("id") Integer Id, Model model) {
		Optional<Game> game = gameService.findGameById(Id);
		Game gg = game.get();
		Map<String, Object> map = new HashMap<String, Object>();
		int favorites_count = gg.getUsersFavourite().size();
		int shares_count = gg.getUsersFavourite().size();
		int reviews = gg.getUsersFavourite().size();
		int views = gg.getUsersFavourite().size();
		map.put("game", gg);
		map.put("favoritesCount", favorites_count);
		map.put("sharesCount", shares_count);
		map.put("numberOfReviews", reviews);
		map.put("pageViews", views);
		model.addAttribute("game", map);
		return "admin_gameDetail";
	}

	@PostMapping("/saveGameDetails")
	public String saveGameDetails(@ModelAttribute("game") Game game, BindingResult result) {
		if (result.hasErrors()) {
			return "admin_gameDetail";
		}
		gameService.save(game);
		return "redirect:/admin/game-list";
	}

	/*
	 * @GetMapping("/addGame") public String showAddGameForm(Model model) {
	 * model.addAttribute("game", new Game()); return "admin_addGame"; }
	 *
	 * @PostMapping("/addGame") public String saveGame(@ModelAttribute("game") Game
	 * game, BindingResult result) { if (result.hasErrors()) { return
	 * "redirect:/game-list"; } return "redirect:/game-list"; }
	 */

	@PostMapping("/approve")
	public String Approve(Game game, BindingResult result) {
		gameService.approveGame(game.getId());
		if (result.hasErrors()) {
			return "redirect:/admin/games-pending-review";
		}
		return "redirect:/admin/games-pending-review";
	}

	@PostMapping("/reject")
	public String Reject(Game game, BindingResult result) {
		gameService.rejectGame(game.getId());

		if (result.hasErrors()) {
			return "redirect:/admin/games-pending-review";
		}
		return "redirect:/admin/games-pending-review";
	}

	@GetMapping("/admin-games-pending-approval")
	public String showPendingGames(Model model) {
		List<Game> pendingGames = gameService.findByGameStatus(Game.GameStatus.PENDING);
		model.addAttribute("pendingGames", pendingGames);
		return "admin_games_pending_approval";
	}

	@GetMapping("/account-list")
	public String account_list(HttpSession sessionObj, Model model) {
		List<Account> accountList_all_User = accountService.findByRoles(User.Role.GAMER);
		List<Map<String, Object>> accountList = new ArrayList<>();

		for (Account acc : accountList_all_User) {
			Map<String, Object> map = new HashMap<>();
			map.put("account", acc);
			System.out.println(acc);
			accountList.add(map);
		}

		model.addAttribute("accounts", accountList);
		model.addAttribute("searchAction", "/gamer-search");
		return "admin_account_list";
	}

	@GetMapping("/developer-list")
	public String Developer_list(HttpSession sessionObj, Model model) {
		List<Account> accountList_all_Developer = accountService.findByRoles(User.Role.DEVELOPER);
		List<Map<String, Object>> accountList = new ArrayList<>();
		for (Account acc : accountList_all_Developer) {
			Map<String, Object> map = new HashMap<>();
			map.put("account", acc);
			System.out.println(acc);
			accountList.add(map);
		}

		model.addAttribute("accounts", accountList);
		model.addAttribute("searchAction", "/developer-search");
		return "admin_account_list";
	}

	@GetMapping("/gamer-search")
	public String admin_searchGamer(@RequestParam("query") String query, Model model) {
		List<Account> searchResults = accountService.searchGamerByName(query);
		List<Map<String, Object>> accountList = new ArrayList<>();
		for (Account acc : searchResults) {
			Map<String, Object> map = new HashMap<>();
			map.put("account", acc);
			accountList.add(map);
		}
		model.addAttribute("accounts", accountList);
		model.addAttribute("searchAction", "/gamer-search");
		return "admin_account_list";
	}

	@GetMapping("/developer-search")
	public String admin_searchDeveloper(@RequestParam("query") String query, Model model) {
		List<Account> searchResults = accountService.searchDeveloperByName(query);
		List<Map<String, Object>> accountList = new ArrayList<>();
		for (Account acc : searchResults) {
			Map<String, Object> map = new HashMap<>();
			map.put("account", acc);
			System.out.println(acc);
			accountList.add(map);
		}
		model.addAttribute("accounts", accountList);
		return "admin_account_list";
	}



	@PostMapping(value = { "/account-ban" })
	public String account_ban(Account acc, HttpSession sessionObj, Model model) {
		List<Account> accountList_all = accountService.findByRoles(User.Role.GAMER);
		List accountList = new ArrayList();
		return "redirect:/admin_account_list";
	}

}
