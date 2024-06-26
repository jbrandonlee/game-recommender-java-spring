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
	
	@OneToOne
	private User user;
	
	public Account(String username, String password, User user) {
		this.username = username;
		this.password = password;
		this.sessionId = null;
		this.user = user;
	}
}