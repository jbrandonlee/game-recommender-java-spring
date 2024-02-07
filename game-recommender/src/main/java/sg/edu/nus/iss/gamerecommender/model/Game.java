package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame.ApprovalStatus;

@Entity
@Data
@NoArgsConstructor
public class Game {
	@Id
	private int id;
	
	private String title;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	private LocalDate dateRelease;
	
	private double price;
	
	private double rating;			// TODO: Can this be dynamically updated from user recommendations
	
	private String imageUrl;
	
	private String webUrl;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Platform.class)
	private List<Platform> platforms;
	
	@ManyToOne
	private User developer;
    private Game game;
    private ProfileGame profileGame;
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Genre.class)
	private List<Genre> genres;
	
	@OneToOne(cascade=CascadeType.ALL)
	private ProfileGame profile;
	
	@OneToMany(mappedBy="gameProfile")
	private Application application;
	
	public enum Platform {
		WINDOWS, MAC, LINUX
	}
	
	public enum Genre {
		ACTION, ADVENTURE, CASUAL, DESIGN, EARLYACCESS, F2P, INDIE, MMO, RACING, RPG, SIMULATION, SPORTS, STRATEGY, UTILITIES 
	}
	
	public Game(int id, String title, String desc, LocalDate release, double price, double rating,
			String imageUrl, String webUrl, List<Platform> platforms, User developer, List<Genre> genres) {
		this.setId(id);
		this.setTitle(title);
		this.setDescription(desc);
		this.setDateRelease(release);
		this.setPrice(price);
		this.setRating(rating);
		this.setImageUrl(imageUrl);
		this.setWebUrl(webUrl);
		this.platforms = platforms;
		this.developer = developer;
		this.genres = genres;
		this.setProfile(new ProfileGame());
	}
		
}

