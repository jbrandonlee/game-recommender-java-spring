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
import sg.edu.nus.iss.gamerecommender.bindingmodel.FormRegister;
import sg.edu.nus.iss.gamerecommender.model.Account;
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
		if (user != null) {
			Role role = user.getRole();
			if (role == Role.ADMIN) {
				return "redirect:/admin/dashboard";
			} else if (role == Role.DEVELOPER) {
				return "redirect:/dev/dashboard";
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
		}
		
		return "login-error";
	}
	
	@GetMapping(value = "/logout")
	public String logout(Model model, HttpSession sessionObj) {
		sessionObj.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping(value = "/register")
	public String register(Model model, HttpSession sessionObj) {
		model.addAttribute("registerForm", new FormRegister());
		return "register";
	}
	
	@PostMapping(value = "/register")
	public String handleRegister(@Valid @ModelAttribute("account") FormRegister registerForm, 
			BindingResult bindingResult, Model model, HttpSession sessionObj) {
		
		// Profile newProfile = profileService.createProfile(new Profile());
		User newUser = userService.createUser(new User(registerForm.getDisplayName(), "", Role.DEVELOPER));
		Account newAccount = accountService.createAccount(new Account(registerForm.getUsername(), registerForm.getUsername(), newUser));
		
		return "login";
	}

}
