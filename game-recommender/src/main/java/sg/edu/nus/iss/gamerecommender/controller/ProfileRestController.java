package sg.edu.nus.iss.gamerecommender.controller; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.AccountService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping("api/profile")
public class ProfileRestController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/friends")
	public ResponseEntity<List<User>> getFriends(@RequestBody String body){
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

}
