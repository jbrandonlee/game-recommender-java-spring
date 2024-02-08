package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameService {
	public List<Game> findAllSortedTopRating();
	public List<Game> findGamesByDevId(int id);
	public List<Game> searchGames(String query);
	public List<Game> findAllGames();
	public Game findGameById (int gameId);
}
