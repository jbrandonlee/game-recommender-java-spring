package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.service.ActivityService;

@RestController
@RequestMapping("/api/user")
public class GamerRestController {
	
	
	@Autowired
	ActivityService activityService;
	
	@PostMapping("/activity")
	public ResponseEntity<?> getAllActivity(@RequestBody String body){
		try {
    		JsonObject genreJson=JsonParser.parseString(body).getAsJsonObject();
    		int userId = genreJson.get("userId").getAsInt();
    		
    		List<Activity> activityFeed = activityService.findUserActivity(userId);
    		
    		return ResponseEntity.ok(activityFeed);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    	}
	}
	
//	@PostMapping("/activity/store")
//	public ResponseEntity<?> storeNewActivity(@RequestBody String body){
//		try {
//			JsonObject json=JsonParser.parseString(body).getAsJsonObject();
//			int userId=json.get("userId").getAsInt();
//			String text=json.get("text").getAsString();
//			
//			activityService.
//		}
//	}
}
