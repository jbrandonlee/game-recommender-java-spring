package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Notification;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;
import sg.edu.nus.iss.gamerecommender.repository.NotificationRepository;

@Service
@Transactional
public class GameServiceImpl implements GameService {

	@Autowired
	GameRepository gameRepo;
	@Autowired
	NotificationRepository ntRepo;
	
	public List<Game> findGamesByDevId(int id) {
		return gameRepo.findGamesByDevId(id);
	}

	@Override
	public List<Game> findAllGames() {
		return gameRepo.findAll();
	}

	@Override
	public List<Game> findTop10ByRating() {
		return gameRepo.findTop10Games(PageRequest.of(0, 10));
	}

	@Override
	public List<Game> searchGames(String query) {
		return gameRepo.findByTitle(query);
	}

	@Override
	public Optional<Game> findGameById(Integer gameId) {
		return gameRepo.findById(gameId);
	}

	@Override
	public void save(Game game) {
		gameRepo.save(game);
	}

	@Override
	public List<Game> findByGameStatus(Game.GameStatus gameStatus) {
		return gameRepo.findByGameStatus(gameStatus);
	}

	@Override
	public void approveGame(Integer gameId) {
		gameRepo.updateGameStatus(gameId, Game.GameStatus.APPROVED);
		Optional<Game> repo = gameRepo.findById(gameId);
		Game game=repo.get();
		User user = game.getDeveloper();
		Notification nt = new Notification("Your game has been approved", "Your game application has been approved. Congratulations to you.", Notification.NotificationType.GAME_APPROVED, user);
		ntRepo.save(nt);
	}

	@Override
	public void rejectGame(Integer gameId) {
		gameRepo.updateGameStatus(gameId, Game.GameStatus.REJECTED);
		Optional<Game> repo = gameRepo.findById(gameId);
		Game game=repo.get();
		User user = game.getDeveloper();
		Notification nt = new Notification("Your game has been rejected", "Your game application has been rejected, please revise it and submit it for review.", Notification.NotificationType.GAME_REJECTED, user);
		ntRepo.save(nt);
	}

	@Override
	public List<Game> searchGamesByTerm(String searchTerm) {
		return gameRepo.searchByGameNameOrDeveloperName(searchTerm);
	}


}
