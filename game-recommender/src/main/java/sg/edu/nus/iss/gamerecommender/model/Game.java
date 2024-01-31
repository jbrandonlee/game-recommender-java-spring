package sg.edu.nus.iss.gamerecommender.model;

import java.util.Date;
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
	
	private Date dateRelease;
	
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
	
	@ElementCollection(targetClass = String.class)
	private List<String> genres;
	
	public enum Platform {
		WINDOWS, MAC, LINUX
	}
	
	public Game(int id, String title, String desc, Date release, double price, double rating,
			String imageUrl, String webUrl, List<Platform> platforms, String developer,
			List<String> categories, List<String> genres) {
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

