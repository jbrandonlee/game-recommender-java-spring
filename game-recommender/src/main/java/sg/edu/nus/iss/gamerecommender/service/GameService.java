package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameService {
	public List<Game> findGamesByDevId(int id);
}
