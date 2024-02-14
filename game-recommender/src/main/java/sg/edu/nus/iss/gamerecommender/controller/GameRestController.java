package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.repository.ProfileRepository;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.PostService;
import sg.edu.nus.iss.gamerecommender.service.RecommenderService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping("api/game")
public class GameRestController {
	
	@Autowired
	RecommenderService reccService;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	ProfileRepository profileRepo;
	
	private Gson gson = new Gson();
	
	@GetMapping("/recommender/{id}")
	public List<Game> getRelatedGames(@PathVariable("id") Integer gameId) {		
		gameId = 550;
		// Get List of Ids from ML endpoint
		String idList = reccService.getRelatedGameIds(gameId);
		System.out.println(idList);
		
		// Get List<Game> from Game Service
		// return gameService.findGamesByIdList(idList);
		return null;
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Game>> findAll() {
	    List<Game> games = gameService.findAllGames();
	    if (games.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.ok(games); 
	}
	
	@PostMapping("/detail")
	public ResponseEntity<Game> getGameDetail(@RequestBody String body){
		try {
			JsonObject inGameIdJson = JsonParser.parseString(body).getAsJsonObject();
			int gameId = inGameIdJson.get("gameId").getAsInt();
			
			Game game = gameService.findGameById(gameId);
			if (game != null) {
			
	           	return ResponseEntity.ok(game);
		
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
	}

	@PutMapping("/follow/{gameId}")
    public ResponseEntity<?> followGame(@PathVariable("gameId") int gameId, @RequestBody String body){
    	try {
    		JsonObject inUserIdJson = JsonParser.parseString(body).getAsJsonObject();
    		int userId = inUserIdJson.get("userId").getAsInt();
    		
    		userService.followGame(userId, gameId);
    		
    		return ResponseEntity.ok(null);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    	}
    }
    
    @PutMapping("/unfollow/{gameId}")
    public ResponseEntity<?> unfollowGame(@PathVariable("gameId") int gameId, @RequestBody String body){
    	try {
    		JsonObject inUserIdJson = JsonParser.parseString(body).getAsJsonObject();
    		int userId = inUserIdJson.get("userId").getAsInt();
    		
    		userService.unfollowGame(userId, gameId);
    		
    		return ResponseEntity.ok(null);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    	}
    }
    
    @PutMapping("/follow-game/{gameId}")
    public void followGame(@PathVariable("gameId") int gameId, HttpSession session){
    	User user = (User) session.getAttribute("user");
    	userService.followGame(user.getId(), gameId);
    }
    
    @PutMapping("/unfollow-game/{gameId}")
    public void unfollowGame(@PathVariable("gameId") int gameId, HttpSession session){
    	User user = (User) session.getAttribute("user");
    	userService.unfollowGame(user.getId(), gameId);
    }
    
    @PostMapping("/review")
    public ResponseEntity<?> postReview(@RequestBody String body) {
        try {
        	JsonObject inReviewJson = JsonParser.parseString(body).getAsJsonObject();	
        	String reviewTitle = "";
        	String reviewMessage = inReviewJson.get("message").getAsString();
        	String reviewGameTitle = inReviewJson.get("gameTitle").getAsString();
        	int reviewUserId = inReviewJson.get("userId").getAsInt();
        	int reviewGameId = inReviewJson.get("gameId").getAsInt();
        	boolean reviewUserFeedback = inReviewJson.get("userFb").getAsBoolean();
        	
        	PostGameReview newReview = postService.createPostGameReview(new PostGameReview(reviewTitle, reviewMessage, 
        			profileRepo.findProfileByUserId(reviewUserId), profileRepo.findProfileByGameTitle(reviewGameTitle), 
        			reviewUserFeedback), reviewUserId, reviewGameId);
        			
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    @PutMapping("/review/update/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewId") int reviewId, @RequestBody String body) {
        try {
        	JsonObject inReviewJson = JsonParser.parseString(body).getAsJsonObject();
        	String message = inReviewJson.get("message").getAsString();
        	Boolean feedback = inReviewJson.get("feedback").getAsBoolean();
        	int userId = inReviewJson.get("userId").getAsInt();
        	int gameId = inReviewJson.get("gameId").getAsInt();
        			
        	PostGameReview existingReview = postService.findReviewById(reviewId);
        	existingReview.setMessage(message);
        	existingReview.setIsRecommend(feedback);
        	
        	PostGameReview updatedReview = postService.createPostGameReview(existingReview, userId, gameId);
        	
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    @DeleteMapping("/review/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") int reviewId) {
        try {
        	postService.deletePostGameReview(reviewId);
        			
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


}
