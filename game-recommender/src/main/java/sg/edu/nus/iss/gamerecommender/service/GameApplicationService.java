package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.GameApplication;
import sg.edu.nus.iss.gamerecommender.model.GameApplication.ApprovalStatus;

public interface GameApplicationService {
	public GameApplication createGameApplication(GameApplication gameApplication);
	public GameApplication updateGameApplication(GameApplication gameApplication);
//	public void deleteGameApplication(GameApplication gameApplication);
	public GameApplication findById(int id);
	public GameApplication findByIdAndDevId(int id, int devId);
	public List<GameApplication> findAll();
	public List<GameApplication> findAllPending();
	public List<GameApplication> findAllByDevId(int devId);
	public List<GameApplication> findPendingByDevId(int devId);
	public GameApplication findPendingByGameId(int gameId);
	public List<GameApplication> findByDevIdAndStatus(int devId, ApprovalStatus status);
	public Integer countAllPending();
	public Integer countPendingByDevId(int devId);
}
