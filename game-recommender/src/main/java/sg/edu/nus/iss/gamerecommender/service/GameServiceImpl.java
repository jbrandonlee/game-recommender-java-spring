package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;

@Service
@Transactional
public class GameServiceImpl implements GameService {

	@Autowired
	GameRepository gameRepository;
	
	public List<Game> findGamesByDevId(int id) {
		return gameRepository.findGamesByDevId(id);
	}


	public Game applyGame(Game game){
		return gameRepository.save(game);
	}

	public List<Game> findAllLeaveApplication() {
		return gameRepository.findAll();
	}

	public Game findById(Integer id) {
		return gameRepository.findById(id).orElse(new Game());
	}


	@jakarta.transaction.Transactional
	public Game editGame(Game game) {
		return gameRepository.saveAndFlush(game);
	}

	public void removeGame(Integer id) {
		gameRepository.deleteById(id);
	}

	public Page<Game> findGamePage(Integer devId, int pageNo, int pageSize, String sortField,
		String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
			: Sort.by(sortField).descending();

	
		PageRequest pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return gameRepository.findGameByDevId(devId, pageable);
	}

	public Long getGameCount(List<ProfileGame.ApprovalStatus> approvalStatus,int devId){
		return gameRepository.countByStatus(approvalStatus,devId);
	}


}
