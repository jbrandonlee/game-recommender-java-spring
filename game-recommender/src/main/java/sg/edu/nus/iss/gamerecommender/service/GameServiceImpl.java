package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;

@Service
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

	@Autowired
	GameRepository gameRepo;
	
	public List<Game> findGamesByDevId(int id) {
		return gameRepo.findGamesByDevId(id);
	}
   
//	public ProfileGame createNewGameProfile (ProfileGame profileGame) {
//		
//	   
//	   profileGame.setAprovalStatus(ProfileGame.ApprovalStatus.APPLIED);
//	   
//	   return profileGame;
	   
   //}
	
	public Game findApprovedGameById(int id) {
		
		return gameRepo.findApprovedGameById(id);
	}
	
	public Game createNewGame (Game game) {
		   
		   return gameRepo.save(game);
		  	   
	   }
	
	public Game editGame(Game game) {
	
	return gameRepo.saveAndFlush(game);
	
	}
	
	public void publishGame(int id, Game game) {
		Game existingGame=gameRepo.findApprovedGameById(id);
		if (existingGame!=null) {
		 existingGame.setTitle(game.getTitle());	
		 existingGame.setImageUrl(game.getImageUrl());	
		 existingGame.setDescription(game.getDescription());
		 //??existingGame.setApplication(game.getApplication()); 
		 gameRepo.saveAndFlush(existingGame);
		}
	}
	
	public Game findGameById(int id) {
		return findGameById( id);
	}
	
}
