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
import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.model.Activity.ActivityType;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.Game.Platform;
import sg.edu.nus.iss.gamerecommender.model.GameApplication;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.repository.ActivityRepository;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;

@Service
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

	@Autowired
	GameRepository gameRepo;
	
	@Autowired
	ActivityRepository activityRepo;
		
	@Override
	@Transactional(readOnly = false)
	public Game createGame(Game game) {
		return gameRepo.saveAndFlush(game);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Game updateGame(Game game) {
		return gameRepo.saveAndFlush(game);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Game publishGameFromGameApplication(GameApplication gameApplication) {
		Game game = new Game();
				
		// If Game already exists, retrieve previous game data and update 
		if (gameApplication.getGameId() != 0) {
			game = findGameById(gameApplication.getGameId());
		}
	
		game.setTitle(gameApplication.getTitle());
		game.setDescription(gameApplication.getDescription());
		game.setDateRelease(gameApplication.getDateRelease());
		game.setPrice(gameApplication.getPrice());
		game.setImageUrl(gameApplication.getImageUrl());
		game.setWebUrl(gameApplication.getWebUrl());
		game.setPlatforms(new ArrayList<Platform>(gameApplication.getPlatforms()));
		game.setGenres(new ArrayList<Genre>(gameApplication.getGenres()));
		game.setDeveloper(gameApplication.getDeveloper());
		
		if (gameApplication.getGameId() != 0) {
			return updateGame(game);
		}
		
		Game newGame = createGame(game);
		User dev = game.getDeveloper();
		Activity activity = new Activity(ActivityType.DEV_CREATE_GAME_PAGE, dev.getId(), dev.getDisplayName(), newGame.getId(), newGame.getTitle());
		activityRepo.saveAndFlush(activity);
		
		return newGame;
	}
		
	public Game findGameById(int id) {
		return gameRepo.findById(id).orElse(null);
	}
	
	public List<Game> findAllSortedTopRating() {
		return gameRepo.findAllSortedTopRating();
	}
	
	public List<Game> findGamesByDevId(int id) {
		return gameRepo.findGamesByDevId(id);
	}
	
	public Page<Game> findGamesByDevId(int id, int pageNo, int pageSize) {
		return gameRepo.findGamesByDevId(id, PageRequest.of(pageNo-1,  pageSize));
	}
	
	public List<Integer> findGameIdsByDevId(int devId) {
		return gameRepo.findGameIdsByDevId(devId);
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
