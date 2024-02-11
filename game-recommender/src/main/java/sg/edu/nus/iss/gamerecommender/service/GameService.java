package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameService {
	public Game findGameById(int id);
	public List<Game> findAllSortedTopRating();
	public List<Game> findGamesByDevId(int id);
	public List<Game> searchGames(String query);
	public List<Game> findAllGames();
	public Page<Game> findTopRated(int pageNo, int pageSize);
	public Page<Game> findTopRatedByDevId(int devId, int pageNo, int pageSize);
	public Page<Game> findTopFollowed(int pageNo, int pageSize);
	public Page<Game> findTopFollowedByDevId(int devId, int pageNo, int pageSize);
	public List<IGenreCount> countGameGenreDistributionByDevId(int devId);
}
