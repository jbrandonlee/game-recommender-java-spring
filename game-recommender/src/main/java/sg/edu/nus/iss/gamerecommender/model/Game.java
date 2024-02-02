package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Game {
	@Id
	private int id;
	
	private String title;
	
	private String description;
	
	private LocalDate dateRelease;
	
	private double price;
	
	private double rating;
	
	private String imageUrl;
	
	private String webUrl;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Platform.class)
	private List<Platform> platforms;
	
	private String developer;
	
	@ElementCollection(targetClass = String.class)
	private List<String> categories;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Genre.class)
	private List<Genre> genres;
	
	// private ProfileGame profile;
	
	public enum Platform {
		WINDOWS, MAC, LINUX
	}
	
	public enum Genre {
		ACTION, RPG
	}
	
	public Game(int id, String title, String desc, LocalDate release, double price, double rating,
			String imageUrl, String webUrl, List<Platform> platforms, String developer,
			List<String> categories, List<Genre> genres) {
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
		this.categories = categories;
		this.genres = genres;		
	}
}

