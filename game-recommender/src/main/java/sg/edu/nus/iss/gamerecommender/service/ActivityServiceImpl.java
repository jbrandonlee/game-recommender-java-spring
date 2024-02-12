package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.repository.ActivityRepository;

@Service
@Transactional(readOnly = true)
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	ActivityRepository activityRepo;
	
	public Page<Activity> findUserActivity(int userId, int pageNo, int pageSize) {
		return activityRepo.findUserActivityPaged(userId, PageRequest.of(pageNo-1, pageSize));
	}
	
	@Override
	public List<Activity> findUserActivity(int userId){
		return activityRepo.findUserActivity(userId);
	}
}
