package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
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

	@ManyToMany
	@JoinTable(
			name = "user_favourites",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "game_id")
	)
	private List<Game> favourites;

	@ManyToMany
	@JoinTable(
			name = "user_wishlist",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "game_id")
	)
	private List<Game> wishlist;

	@ManyToMany
	@JoinTable(
			name = "user_followGames",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "game_id")
	)
	private List<Game> followGames;

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
