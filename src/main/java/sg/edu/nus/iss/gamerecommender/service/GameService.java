package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameService {
	public List<Game> findGamesByDevId(int id);
	
	public List<Game> findAllGames();

	public List<Game> findTop10ByRating();
	public List<Game> searchGames(String query);
	public Optional<Game> findGameById(Integer gameId);
	public void save(Game game);
	public List<Game> findByGameStatus(Game.GameStatus gameStatus);
	public void approveGame(Integer gameId);
	public void rejectGame(Integer gameId);
	public List<Game> searchGamesByTerm(String searchTerm);

}
