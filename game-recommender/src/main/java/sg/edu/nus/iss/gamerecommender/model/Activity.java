package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Activity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String message;
	
	private LocalDateTime timeCreated;
	
	@Enumerated(EnumType.STRING)
	private ParentType parentType;
	private int parentId;
	private String parentName;
	
	@Enumerated(EnumType.STRING)
	private TargetType targetType;
	private int targetId;
	private String targetName;
	
	@Enumerated(EnumType.STRING)
	private ActivityType activityType;	
	
	public enum ParentType {
		USER, GAME
	}
	
	public enum TargetType {
		USER, GAME, POST 
	}

	public enum ActivityType {
		USER_FOLLOW_GAME, USER_FOLLOW_DEV, USER_FOLLOW_USER, 
		USER_CREATE_GAME_REVIEW, DEV_CREATE_GAME_PAGE, GAME_UPDATE_POST
	}
	
	public Activity(ActivityType activityType, int parentId, String parentName, int targetId, String targetName) {
		this.timeCreated = LocalDateTime.now();
		this.activityType = activityType;
		this.parentId = parentId;
		this.parentName = parentName;
		this.targetId = targetId;
		this.targetName = targetName;
		
		switch (activityType) {
			case USER_FOLLOW_GAME:
				this.parentType = ParentType.USER;
				this.targetType = TargetType.GAME;
				this.message = "has just followed a Game:";
				break;
			case USER_FOLLOW_DEV:
				this.parentType = ParentType.USER;
				this.targetType = TargetType.USER;
				this.message = "has just followed a Developer:";
				break;
			case USER_FOLLOW_USER:
				this.parentType = ParentType.USER;
				this.targetType = TargetType.USER;
				this.message = "has just followed a User:";
				break;
			case USER_CREATE_GAME_REVIEW:
				this.parentType = ParentType.USER;
				this.targetType = TargetType.GAME;
				this.message = "has just posted a Game Review for:";
				break;
			case DEV_CREATE_GAME_PAGE:
				this.parentType = ParentType.USER;
				this.targetType = TargetType.GAME;
				this.message = "has just created a new Game Page:";
				break;
			case GAME_UPDATE_POST:
				this.parentType = ParentType.GAME;
				this.targetType = TargetType.POST;
				this.message = "has just posted an Update.";
				break;
			default:
				break;
		}
	}
}
