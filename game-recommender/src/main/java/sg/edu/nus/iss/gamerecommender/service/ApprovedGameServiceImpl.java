package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.ApprovedGame;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;
import sg.edu.nus.iss.gamerecommender.repository.ApprovedGameRepository;

@Service
@Transactional
public class ApprovedGameServiceImpl implements ApprovedGameService {

	@Autowired
	ApprovedGameRepository approvedgameRepository;
	
	public List<ApprovedGame> findGamesByDevId(int id) {
		return approvedgameRepository.findGamesByDevId(id);
	}

	public ApprovedGame applyGame(ApprovedGame game){
		return approvedgameRepository.save(game);
	}

	public List<ApprovedGame> findAllLeaveApplication() {
		return approvedgameRepository.findAll();
	}

	public ApprovedGame findById(Integer id) {
		return approvedgameRepository.findById(id).orElse(new ApprovedGame());
	}


	@jakarta.transaction.Transactional
	public ApprovedGame editGame(ApprovedGame game) {
		return approvedgameRepository.saveAndFlush(game);
	}

	public void removeGame(Integer id) {
		approvedgameRepository.deleteById(id);
	}

	public Page<ApprovedGame> findGamePage(Integer devId, int pageNo, int pageSize, String sortField,
		String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
			: Sort.by(sortField).descending();

	
		PageRequest pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return approvedgameRepository.findGameByDevId(devId, pageable);
	}

	public Long getGameCount(List<ProfileGame.ApprovalStatus> approvalStatus,int devId){
		return approvedgameRepository.countByStatus(approvalStatus,devId);
	}


}