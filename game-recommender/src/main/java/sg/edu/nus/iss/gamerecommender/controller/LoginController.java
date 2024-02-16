package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.gamerecommender.dto.FormRegisterDev;
import sg.edu.nus.iss.gamerecommender.dto.FormRegisterGamer;
import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.AccountService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;
	
	@GetMapping(value = { "/", "/login", "/home" })
	public String login(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		
		// If user already logged in
		if (user != null) {
			Role role = user.getRole();
			if (role == Role.ADMIN) {
				return "redirect:/admin/dashboard";
			} else if (role == Role.DEVELOPER) {
				return "redirect:/dev/dashboard";
			} else if (role == Role.GAMER) {
				return "redirect:/user/home";
			}
		}
		
		model.addAttribute("account", new Account());
		return "login";
	}
	
	@PostMapping(value = "/login")
	public String handleLogin(@Valid @ModelAttribute("account") Account accountForm, BindingResult bindingResult,
								Model model, HttpSession sessionObj) {
		
		if (bindingResult.hasErrors()) {
			return "login";
		}
		
		Account account = accountService.authenticate(accountForm.getUsername(), accountForm.getPassword());
		if (account == null) {
			model.addAttribute("errorMsg", "Invalid Username or Password. Please try again.");
			return "login";
		}
		
		User user = userService.findUserByAccount(account);
		sessionObj.setAttribute("account", account);
		sessionObj.setAttribute("user", user);
		
		Role role = user.getRole();
		if (role == Role.ADMIN) {
			return "redirect:/admin/dashboard";
		} else if (role == Role.DEVELOPER) {
			return "redirect:/dev/dashboard";
		} else if (role == Role.GAMER) {
			return "redirect:/user/home";
		}
		
		return "login-error";
	}
	
	@GetMapping(value = "/logout")
	public String logout(Model model, HttpSession sessionObj) {
		sessionObj.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping(value = "/register-dev")
	public String registerDev(Model model, HttpSession sessionObj) {
		model.addAttribute("registerForm", new FormRegisterDev());
		return "register-dev";
	}
	
	@PostMapping(value = "/register-dev")
	public String handleRegisterDev(@Valid @ModelAttribute("registerForm") FormRegisterDev registerForm, 
			BindingResult bindingResult, Model model, HttpSession sessionObj) {
		
		User newUser = userService.createUser(new User(registerForm.getDisplayName(), "", Role.DEVELOPER));
		Account newAccount = accountService.createAccount(new Account(registerForm.getUsername(), registerForm.getPassword(), newUser));
		
		return "login";
	}
	

	@GetMapping(value = "/register-gamer")
	public String registerGamer(Model model, HttpSession sessionObj) {
		model.addAttribute("genreList", Genre.values());
		model.addAttribute("registerForm", new FormRegisterGamer());
		return "register-gamer";
	}
	
	@PostMapping(value = "/register-gamer")
	public String handleRegisterGamer(@Valid @ModelAttribute("registerForm") FormRegisterGamer registerForm, 
			BindingResult bindingResult, Model model, HttpSession sessionObj) {
		
		User newUser = new User(registerForm.getDisplayName(), "", Role.GAMER);
		((ProfileGamer) newUser.getProfile()).setGenrePreferences(registerForm.getGenrePreferences());
		newUser = userService.createUser(newUser);
		
		System.out.println(registerForm.getGenrePreferences());
		
		Account newAccount = accountService.createAccount(new Account(registerForm.getUsername(), registerForm.getPassword(), newUser));
		
		return "login";
	}
}
