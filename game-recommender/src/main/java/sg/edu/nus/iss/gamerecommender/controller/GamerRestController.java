package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.service.ActivityService;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.RecommenderService;

@RestController
@RequestMapping("/api/user")
public class GamerRestController {
	@Autowired
	GameService gameService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	RecommenderService recoService;
	
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
	
	@GetMapping("/home/topgamelist")
	public List<Game> getAllTopGame(){
		Page<Game> topRatedGames = gameService.findTopRated(1, 3);
		return topRatedGames.getContent();
	}
	
	@GetMapping("/home/trendgamelist")
	public List<Game> getAllTrendGame(){
		Page<Game> topTrendGames=gameService.findTopFollowed(1, 3);
		return topTrendGames.getContent();
	}
}
	
