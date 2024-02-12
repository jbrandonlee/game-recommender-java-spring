package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.GameApplication;
import sg.edu.nus.iss.gamerecommender.model.GameApplication.ApprovalStatus;

public interface GameApplicationService {
	public GameApplication findById(int id);
	public GameApplication createGameApplication(GameApplication gameApplication);
	public GameApplication updateGameApplication(GameApplication gameApplication);
	public List<GameApplication> findAllByDevId(int devId);
	public List<GameApplication> findPendingByDevId(int devId);
	public List<GameApplication> findByDevIdAndStatus(int devId, ApprovalStatus status);
}
