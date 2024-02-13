package sg.edu.nus.iss.gamerecommender.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.iss.gamerecommender.repository.AccountRepository;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;
import sg.edu.nus.iss.gamerecommender.repository.PostRepository;
import sg.edu.nus.iss.gamerecommender.repository.UserRepository;
import sg.edu.nus.iss.gamerecommender.model.*;
import sg.edu.nus.iss.gamerecommender.model.User.Role;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService{

	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private GameRepository gameRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public long getTotalGamers() {
		List<User> all = userRepo.findAll();
		long count =0;
		for(User cc:all){
			if(cc.getRole()== Role.GAMER){
				count++;
			}
		}
		return count;
	}

	@Override
	public long getTotalDevelopers() {
		List<User> all = userRepo.findAll();
		long count =0;
		for(User cc:all){
			if(cc.getRole()== Role.DEVELOPER){
				count++;
			}
		}
		return count;
	}

	@Override
	public long getTotalGames() {
		return gameRepo.count();
	}

	@Override
	public long getGamesPendingReview() {
		return gameRepo.countByPendingGames(Game.GameStatus.PENDING);
	}


	@Override
	public long getTotalAccountGame(String name) {
		Account account = accRepo.searchByName(name);
		List<Game> games = gameRepo.searchByGameNameOrDeveloperName(account.getUsername());
		System.out.println("------------------------------------------");
		System.out.println(games.size());
		System.out.println("------------------------------------------");
		return games.size();
	}

	@Override
	public double getAvgAccountGame(String name) {
		Account account = accRepo.searchByName(name);
		List<Game> games = gameRepo.searchByGameNameOrDeveloperName(account.getUsername());
		double sum_rating=0;
		for(Game game:games){
			sum_rating=sum_rating+game.getRating();
		}
		return sum_rating/games.size();
	}

}
