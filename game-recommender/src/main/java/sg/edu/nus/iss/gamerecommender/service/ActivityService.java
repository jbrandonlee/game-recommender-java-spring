package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.nus.iss.gamerecommender.model.Activity;

public interface ActivityService {
	public Page<Activity> findUserActivity(int userId, int pageNo, int pageSize);
	public List<Activity> findUserActivity(int userId);
	public List<Integer> countPastWeekNewAccountFollowersByDevId(int devId);
	public List<Integer> countPastWeekNewGameFollowersByDevId(int devId);
}
