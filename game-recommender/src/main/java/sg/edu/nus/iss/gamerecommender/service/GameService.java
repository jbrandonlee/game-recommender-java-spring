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
	public Page<String> findTopRatedTitles(int pageNo, int pageSize);
	public Page<Double> findTopRatedRatings(int pageNo, int pageSize);
	public Page<Game> findTopRatedByDevId(int devId, int pageNo, int pageSize);
	public Page<String> findTopRatedTitlesByDevId(int devId, int pageNo, int pageSize);
	public Page<Double> findTopRatedRatingsByDevId(int devId, int pageNo, int pageSize);	
	
	public Page<Game> findTopFollowed(int pageNo, int pageSize);
	public Page<String> findTopFollowedTitles(int pageNo, int pageSize);
	public Page<Integer> countTopFollowedTitlesFollowers(int pageNo, int pageSize);
	public Page<Game> findTopFollowedByDevId(int devId, int pageNo, int pageSize);
	public Page<String> findTopFollowedTitlesByDevId(int devId, int pageNo, int pageSize);
	public Page<Integer> countTopFollowedTitlesFollowersByDevId(int devId, int pageNo, int pageSize);
	
	public List<IGenreCount> countGameGenreDistributionByDevId(int devId);
	public List<Integer> countPastWeekNewGamePages();
	public Integer countAllGames();
	public Integer countGamesByDevId(int devId);
	public Double getAverageGameRatingByDevId(int devId);
	
	
}
