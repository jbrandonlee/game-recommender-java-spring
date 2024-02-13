package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;

import jakarta.persistence.*;
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

	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@ManyToOne
	private User user;
	
	public enum NotificationType {
		// For Admin & Devs
		NEW_GAME_PENDING, GAME_APPROVED, GAME_REJECTED,
		NEW_BAN_PENDING, BAN_APPROVED, BAN_REJECTED,
		// For User
		NEW_GAME_RELEASE, NEW_WARNING
	}

	public Notification(String title, String message, NotificationType type, User user) {
		this.title = title;
		this.message = message;
		this.date = LocalDate.now();
		this.user = user;
		this.type=type;
	}
}


