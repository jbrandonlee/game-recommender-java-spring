package sg.edu.nus.iss.gamerecommender.controller;

import java.time.LocalDate;
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
	RecommenderService recommenderService;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	ProfileRepository profileRepo;
	
	private Gson gson = new Gson();
	
	//Retrieve related games on game detail page
	@PostMapping("/recommender/game")
	public ResponseEntity<List<Game>> getRelatedGames(@RequestBody String body) {		
		JsonObject genreJson=JsonParser.parseString(body).getAsJsonObject();
		int gameId = genreJson.get("gameId").getAsInt();
		List<Game> recommendations = recommenderService.getRelatedGames(gameId, 3, true);
		
		return ResponseEntity.ok(recommendations);
	}
	
	//Retrieve games recommendations on home page
	@PostMapping("/recommender/user")
	public ResponseEntity<List<Game>> getUserRecommendations(@RequestBody String body) {
		JsonObject genreJson=JsonParser.parseString(body).getAsJsonObject();
		int userId = genreJson.get("userId").getAsInt();
		List<Game> recommendations = recommenderService.getUserRecommendations(userId, 3, true);
	
		return ResponseEntity.ok(recommendations);
	}
		
	//Retrieve games list on game page
	@GetMapping("/list")
	public ResponseEntity<List<Game>> findAll() {
	    List<Game> games = gameService.findAllGames();
	    if (games.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.ok(games); 
	}
	
	//Retrieve game detail
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

	//Follow game
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
    
	//Unfollow game
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
    
    //Post review on game detail page
    @PostMapping("/{gameId}/review")
    public ResponseEntity<?> createGameReviewPost(@PathVariable("gameId") int gameId, @RequestBody String body) {
        try {
        	JsonObject inReviewJson = JsonParser.parseString(body).getAsJsonObject();
        	int userId = inReviewJson.get("userId").getAsInt();
        	String message = inReviewJson.get("message").getAsString();
        	Boolean feedback = inReviewJson.get("userFb").getAsBoolean(); 
        	
        	User user = userService.findUserById(userId);
        	Game game = gameService.findGameById(gameId);
        	
        	PostGameReview gameReviewPost = postService.findReviewPostByGameAndUserId(user.getId(), game.getId());		
        	
        	if (gameReviewPost == null) {
    			gameReviewPost = new PostGameReview();
    		}
        	
        	gameReviewPost.setUserProfile(user.getProfile());
    		gameReviewPost.setGameProfile(game.getProfile());
    		gameReviewPost.setMessage(message);
    		gameReviewPost.setIsRecommend(feedback);
    		gameReviewPost.setDatePosted(LocalDate.now());
    		
    		postService.createPostGameReview(gameReviewPost, user.getId(), game.getId());
        	
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    //Delete review
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
