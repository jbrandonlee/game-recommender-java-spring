package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.Game.Platform;

@Entity
@Data
@NoArgsConstructor
public class GameApplication {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int gameId;
	
	private String title;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	private LocalDate dateRelease;
	
	private double price;
	
	private String imageUrl;
	
	private String webUrl;

	@ManyToOne
	@JsonBackReference
	private User developer;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Platform.class)
	private List<Platform> platforms;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Genre.class)
	private List<Genre> genres;
	
	@Enumerated(EnumType.STRING)
	private ApprovalStatus approvalStatus;
	
	private String approverComments;
	
	public enum ApprovalStatus {
		APPLIED, UPDATED, APPROVED, REJECTED, DELETED
	}
	
//	public GameApplication(String title, String desc, LocalDate release, double price,
//			String imageUrl, String webUrl, User developer, List<Platform> platforms, List<Genre> genres) {
//		this.title = title;
//		this.description = desc;
//		this.dateRelease = release;
//		this.price = price;
//		this.imageUrl = imageUrl;
//		this.webUrl = webUrl;
//		this.platforms = platforms;
//		this.developer = developer;
//		this.genres = genres;
//		this.approvalStatus = ApprovalStatus.APPLIED;
//	}
}

