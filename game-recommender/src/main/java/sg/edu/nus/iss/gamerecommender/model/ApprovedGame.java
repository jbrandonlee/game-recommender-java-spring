package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ApprovedGame {
	@Id
	private int id;
	
	private String title;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	private LocalDate dateRelease;
	
	private double price;
	
	private double rating;			
	
	private String imageUrl;
	
	private String webUrl;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Platform.class)
	private List<Platform> platforms;
	
	@ManyToOne
	private User developer;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Genre.class)
	private List<Genre> genres;
	
	@OneToOne(cascade=CascadeType.ALL)
	private ApprovedProfileGame profile;


	public enum Platform {
		WINDOWS, MAC, LINUX
	}
	
	public enum Genre {
		ACTION, ADVENTURE, CASUAL, DESIGN, EARLYACCESS, F2P, INDIE, MMO, RACING, RPG, SIMULATION, SPORTS, STRATEGY, UTILITIES 
	}
	
	public enum ApprovalStatus {
		UPDATED, APPROVED, REJECTED, PULISHED
	}
	
	public ApprovedGame(int id, String title, String desc, LocalDate release, double price, double rating,
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
		this.profile = new ApprovedProfileGame();
	}
}
