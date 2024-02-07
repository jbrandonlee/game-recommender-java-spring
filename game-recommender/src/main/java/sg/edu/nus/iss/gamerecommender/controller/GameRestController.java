package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.service.GameService;
import sg.edu.nus.iss.gamerecommender.service.RecommenderService;

@RestController
@RequestMapping("api/game")
public class GameRestController {
	
	@Autowired
	RecommenderService reccService;
	
	@Autowired
	GameService gameService;
	
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
}
