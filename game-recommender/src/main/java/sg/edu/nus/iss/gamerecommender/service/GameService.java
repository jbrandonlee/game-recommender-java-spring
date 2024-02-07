package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;

public interface GameService {
	public List<Game> findGamesByDevId(int id);
	public Game findApprovedGameById(int id);
	public Game createNewGame(Game game);
	public Game editGame(Game game);
	public void publishGame(int id, Game game);
	public Game findGameById(int id);
}
