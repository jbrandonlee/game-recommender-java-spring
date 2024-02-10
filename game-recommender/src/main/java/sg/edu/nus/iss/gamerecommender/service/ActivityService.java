package sg.edu.nus.iss.gamerecommender.service;

import org.springframework.data.domain.Page;

import sg.edu.nus.iss.gamerecommender.model.Activity;

public interface ActivityService {
	public Page<Activity> findUserActivity(int userId, int pageNo, int pageSize);
}
