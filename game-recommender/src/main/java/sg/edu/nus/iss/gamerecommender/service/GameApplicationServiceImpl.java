package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.GameApplication;
import sg.edu.nus.iss.gamerecommender.model.GameApplication.ApprovalStatus;
import sg.edu.nus.iss.gamerecommender.repository.GameApplicationRepository;

@Service
@Transactional(readOnly = true)
public class GameApplicationServiceImpl implements GameApplicationService {
	
	@Autowired
	GameApplicationRepository gameApplicationRepo;
	
	@Override
	@Transactional(readOnly = false)
	public GameApplication createGameApplication(GameApplication gameApplication) {
		return gameApplicationRepo.save(gameApplication);
	}
	
	@Override
	@Transactional(readOnly = false)
	public GameApplication updateGameApplication(GameApplication gameApplication) {
		return gameApplicationRepo.save(gameApplication);
	}
	
//	@Override
//	@Transactional(readOnly = false)
//	public void deleteGameApplication(GameApplication gameApplication) {
//		gameApplicationRepo.delete(gameApplication);
//	}
	
	public GameApplication findById(int id) {
		return gameApplicationRepo.findById(id).orElse(null);
	}
	
	public List<GameApplication> findAll() {
		return gameApplicationRepo.findAll();
	}
	
	public List<GameApplication> findAllPending() {
		return gameApplicationRepo.findAllPending();
	}
	
	public List<GameApplication> findAllByDevId(int devId) {
		return gameApplicationRepo.findAllByDevId(devId);
	}
	
	public List<GameApplication> findPendingByDevId(int devId) {
		return gameApplicationRepo.findPendingByDevId(devId);
	}
	
	public GameApplication findPendingByGameId(int gameId) {
		return gameApplicationRepo.findPendingByGameId(gameId);
	}
	
	public List<GameApplication> findByDevIdAndStatus(int devId, ApprovalStatus status) {
		return gameApplicationRepo.findByDevIdAndStatus(devId, status);
	}
	
	public Integer countAllPending() {
		return gameApplicationRepo.countAllPending();
	}
	
	public Integer countPendingByDevId(int devId) {
		return gameApplicationRepo.countPendingByDevId(devId);
	}
	
}
