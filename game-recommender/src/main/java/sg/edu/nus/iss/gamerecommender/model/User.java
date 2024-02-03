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
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String displayName;

	private String displayImageUrl;
	
	private LocalDate joinDate;
	
	// @OneToOne
	// private Profile profile;
	
	// @OneToMany
	// private List<Notifications> notifications;
	
	public User(String displayName) {
		this.displayName = displayName;
		this.displayImageUrl = null;
		this.joinDate = LocalDate.now();
	}
}
