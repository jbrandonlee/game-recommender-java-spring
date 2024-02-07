package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping(value = "/api/common")
public class CommonRestController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/search") 
	public ResponseEntity<?> search(@RequestBody String body) {
		try {
			JsonObject searchData = JsonParser.parseString(body).getAsJsonObject();
			String type = searchData.get("type").getAsString();
			String searchQuery=searchData.get("query").getAsString();
			
			switch(type) {
				case "Game":
					List<Game> gameList;
					gameList = gameService.searchGames(searchQuery);
					return ResponseEntity.ok(gameList);
				case "Developer":
					List<User> developerList;
					developerList = userService.searchDevelopers(searchQuery);
					return ResponseEntity.ok(developerList);
				case "User":
					List<User> userList;
					userList = userService.searchGamers(searchQuery);
					return ResponseEntity.ok(userList);
			}
			return null;
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
	}
}
