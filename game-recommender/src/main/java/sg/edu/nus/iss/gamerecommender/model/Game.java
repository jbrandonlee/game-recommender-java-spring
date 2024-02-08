package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	//@JsonBackReference
	private User developer;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Genre.class)
	private List<Genre> genres;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JsonManagedReference
	private ProfileGame profile;
	
	public enum Platform {
		WINDOWS, MAC, LINUX
	}
	
	public enum Genre {
		ACTION, ADVENTURE, CASUAL, DESIGN, EARLYACCESS, F2P, INDIE, MMO, RACING, RPG, SIMULATION, SPORTS, STRATEGY, UTILITIES 
	}
	
	public Game(int id, String title, String desc, LocalDate release, double price, double rating,
			String imageUrl, String webUrl, List<Platform> platforms, User developer, List<Genre> genres) {
		this.id = id;
		this.title = title;
		this.description = desc;
		this.dateRelease = release;
		this.price = price;
		this.rating = rating;
		this.imageUrl = imageUrl;
		this.webUrl = webUrl;
		this.platforms = platforms;
		this.developer = developer;
		this.genres = genres;
		this.profile = new ProfileGame();
	}
}

