package sg.edu.nus.iss.gamerecommender.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
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
	
	public Page<Game> findTopRated(int pageNo, int pageSize) {
		return gameRepo.findTopRated(PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<String> findTopRatedTitles(int pageNo, int pageSize) {
		return gameRepo.findTopRatedTitles(PageRequest.of(pageNo-1, pageSize));
	}

	public Page<Double> findTopRatedRatings(int pageNo, int pageSize) {
		return gameRepo.findTopRatedRatings(PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<Game> findTopRatedByDevId(int devId, int pageNo, int pageSize) {
		return gameRepo.findTopRatedByDevId(devId, PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<String> findTopRatedTitlesByDevId(int devId, int pageNo, int pageSize) {
		return gameRepo.findTopRatedTitlesByDevId(devId, PageRequest.of(pageNo-1, pageSize));
	}

	public Page<Double> findTopRatedRatingsByDevId(int devId, int pageNo, int pageSize) {
		return gameRepo.findTopRatedRatingsByDevId(devId, PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<Game> findTopFollowed(int pageNo, int pageSize) {
		return gameRepo.findTopFollowed(PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<String> findTopFollowedTitles(int pageNo, int pageSize) {
		return gameRepo.findTopFollowedTitles(PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<Integer> countTopFollowedTitlesFollowers(int pageNo, int pageSize) {
		return gameRepo.countTopFollowedTitlesFollowers(PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<Game> findTopFollowedByDevId(int devId, int pageNo, int pageSize) {
		return gameRepo.findTopFollowedByDevId(devId, PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<String> findTopFollowedTitlesByDevId(int devId, int pageNo, int pageSize) {
		return gameRepo.findTopFollowedTitlesByDevId(devId, PageRequest.of(pageNo-1, pageSize));
	}
	
	public Page<Integer> countTopFollowedTitlesFollowersByDevId(int devId, int pageNo, int pageSize) {
		return gameRepo.countTopFollowedTitlesFollowersByDevId(devId, PageRequest.of(pageNo-1, pageSize));
	}
	
	public List<IGenreCount> countGameGenreDistributionByDevId(int devId) {
		return gameRepo.countGameGenreDistributionByDevId(devId);
	}
	
	public List<Integer> countPastWeekNewGamePages() {
		List<Integer> pastWeekNewGamePages = new ArrayList<>();
		for (int i = 6; i >= 0; i--) {
			Integer count = gameRepo.countNewGamePagesOnDate(LocalDate.now().minusDays(i));	
			pastWeekNewGamePages.add(count);
		}
		return pastWeekNewGamePages;
	}

	public Integer countAllGames() {
		return gameRepo.countAllGames();
	}
	
	public Integer countGamesByDevId(int devId) {
		return gameRepo.countGamesByDevId(devId);
	}
	
	public Double getAverageGameRatingByDevId(int devId) {
		return gameRepo.getAverageGameRatingByDevId(devId);
	}
}
