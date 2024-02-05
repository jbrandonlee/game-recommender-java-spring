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
	
	public List<Game> findAllSortedTopRating() {
		return gameRepo.findAllSortedTopRating();
	}
	
	public List<Game> findGamesByDevId(int id) {
		return gameRepo.findGamesByDevId(id);
	}

}
