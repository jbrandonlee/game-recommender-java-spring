package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping(value = "/api/common")
public class CommonRestController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
//	@PostMapping(value = "/search") 
//	public ResponseEntity<?> search(@RequestBody String body) {
//		
//	}
}
