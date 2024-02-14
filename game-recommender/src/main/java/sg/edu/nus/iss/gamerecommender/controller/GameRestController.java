package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.GameService;
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
	
	@GetMapping("/recommender/game/{id}")
	public List<Game> getRelatedGames(@PathVariable("id") Integer gameId) {		
		List<Game> recommendations = recommenderService.getRelatedGames(gameId, 10, true);
		// TODO
		return null;
	}
	
	@GetMapping("/recommender/user/{id}")
	public List<Game> getUserRecommendations(@PathVariable("id") Integer userId) {
		List<Game> recommendations = recommenderService.getUserRecommendations(userId, 10, true);
		// TODO
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
}
