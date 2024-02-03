package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private String message;
	
	private boolean readStatus;
	
	private LocalDate date;
	
	public enum NotificationType {
		// For Admin & Devs
		NEW_GAME_PENDING, GAME_APPROVED, GAME_REJECTED,
		NEW_BAN_PENDING, BAN_APPROVED, BAN_REJECTED,
		// For User
		NEW_GAME_RELEASE, NEW_WARNING
	}
}


