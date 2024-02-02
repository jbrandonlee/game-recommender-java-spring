package sg.edu.nus.iss.gamerecommender.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Account {
	@Id
	private String username;
	
	private String password;
	
	private String sessionId;
	
	private Role role;
	
	@OneToOne
	private User user;
	
	public Account(String username, String password, Role role, User user) {
		this.username = username;
		this.password = password;
		this.sessionId = null;
		this.role = role;
		this.user = user;
	}
	
	public enum Role {
		ADMIN, DEVELOPER, GAMER
	}
}