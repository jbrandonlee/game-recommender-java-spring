package sg.edu.nus.iss.gamerecommender.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.ProfileGamerService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping(value = "/api/user/profile")
public class UserProfileRestController {
	
	@Autowired
	ProfileGamerService gameService;
	
	@Autowired
	UserService userService;

    
    @PostMapping("/genre")
    public ResponseEntity<?> storeGenres(@RequestBody String body){
    	try {
    		JsonObject genreJson=JsonParser.parseString(body).getAsJsonObject();
    		JsonArray jsonArray=genreJson.getAsJsonArray("genreData");
    		int userId = genreJson.get("userId").getAsInt();
    		
    		User user=userService.findUserById(userId);
    		ProfileGamer gamer=(ProfileGamer) user.getProfile();
    		
    		List<Genre> genreList = new ArrayList<>();
    		for (JsonElement element : jsonArray) {
    			String e=element.getAsString();
    			Genre genre=Genre.valueOf(e);
    			genreList.add(genre);
    		}
    		
    		gamer.setGenrePreferences(genreList);
    		gameService.saveProfileGamer(gamer);
    		
    		return new ResponseEntity<User>(HttpStatus.CREATED);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    	}
    }
    
    @GetMapping("/genreList")
    public List<Genre> getGenres(){
    	return Arrays.asList(Genre.values());
    }
    
    @PostMapping("/games")
	public ResponseEntity<List<Game>> getFriends(@RequestBody String body){
		try {
			JsonObject inUserIdJson = JsonParser.parseString(body).getAsJsonObject();
			int userId = inUserIdJson.get("userId").getAsInt();
			
			User user = userService.findUserById(userId);
			if (user != null) {
				ProfileGamer profileGamer = (ProfileGamer) user.getProfile();
				
				List<Game> games = profileGamer.getFollowedGames();
				
	           	return ResponseEntity.ok(games);
		
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
	}
    
    @PostMapping("/friends")
	public ResponseEntity<List<User>> getFollowedGames(@RequestBody String body){
		try {
			JsonObject inUserIdJson = JsonParser.parseString(body).getAsJsonObject();
			int userId = inUserIdJson.get("userId").getAsInt();
			
			User user = userService.findUserById(userId);
			if (user != null) {
				ProfileGamer profileGamer = (ProfileGamer) user.getProfile();
				
				List<User> friends = profileGamer.getFriends();
				
	           	return ResponseEntity.ok(friends);
		
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
	}
    
    @PostMapping("/detail")
	public ResponseEntity<User> getDetail(@RequestBody String body){
		try {
			JsonObject inUserIdJson = JsonParser.parseString(body).getAsJsonObject();
			int userId = inUserIdJson.get("userId").getAsInt();
			
			User user = userService.findUserById(userId);
			if (user != null) {
	           	return ResponseEntity.ok(user);
		
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
	}
    
 
}
