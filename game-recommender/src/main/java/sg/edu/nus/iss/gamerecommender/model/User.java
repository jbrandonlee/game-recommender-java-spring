package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String displayName;

	private String displayImageUrl;
	
	private String biography;
	
	private LocalDate joinDate;
	
	private Role role;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Profile profile;
	
	// @OneToMany
	// private List<Notifications> notifications;
	
	public User(String displayName, String biography, Role role) {
		this.displayName = displayName;
		this.role = role;
		this.biography = biography;
		this.displayImageUrl = "";
		this.joinDate = LocalDate.now();
		
		if (this.role == Role.ADMIN) {
			this.profile = null;
		} else if (this.role == Role.DEVELOPER) {
			this.profile = new ProfileDeveloper();
		} else if (this.role == Role.GAMER) {
			this.profile = new ProfileGamer();
		}
	}
	
	public enum Role {
		ADMIN, DEVELOPER, GAMER
	}
}
