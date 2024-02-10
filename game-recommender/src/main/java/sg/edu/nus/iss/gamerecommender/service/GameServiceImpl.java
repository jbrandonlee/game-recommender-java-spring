package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;

@Service
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

	@Autowired
	GameRepository gameRepo;
	
	public Game findGameById(int id) {
		return gameRepo.findById(id).orElse(null);
	}
	
	public List<Game> findAllSortedTopRating() {
		return gameRepo.findAllSortedTopRating();
	}
	
	public List<Game> findGamesByDevId(int id) {
		return gameRepo.findGamesByDevId(id);
	}
	
	public List<Game> searchGames(String query) {
		return gameRepo.searchGames(query);
	}
	
	public List<Game> findAllGames() {
		return gameRepo.findAll();
	}

}
