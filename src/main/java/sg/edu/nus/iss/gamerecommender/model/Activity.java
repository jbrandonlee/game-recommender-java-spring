package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Activity
// .. UserActivity (PURCHASE, FOLLOW_GAME, FOLLOW_DEV, ADD_FRIEND, CREATE_LIST, NEW_POST)
// .. DevActivity  (NEW_POST (userId, postId), NEW_GAME (userId, gameId))
// .. GameActivity (NEW_POST (gameId, postId))

public class Activity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private LocalDate dateCreated;
	
	private ParentType parentType;
	private int parentId;
	
	private TargetType targetType;
	private int targetId;
	
	private ActivityType activityType;	
	
	public enum ParentType {
		USER, GAME
	}
	
	public enum TargetType {
		USER, GAME, LIST, POST 
	}

	public enum ActivityType {
		PURCHASE, FOLLOW_GAME, FOLLOW_DEV, ADD_FRIEND, CREATE_LIST, USER_POST    
	}
}
