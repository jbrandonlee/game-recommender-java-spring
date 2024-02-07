package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame.ApprovalStatus;


@Data
@Entity
public class Application {
	private Game game;
	
	@ManyToOne
	private ProfileGame profileGame;
	
	@Column(columnDefinition="TEXT")
	private String description;
	@Enumerated(EnumType.STRING)
	
    private String imageUrl;
	
	private boolean visibilityStatus;
	
    @Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Genre.class)
	private List<Genre> genres;
	
    private ApprovalStatus approvalStatus;
	//private List<PostGame> gameUpdatePosts;
	
	
	private List<PostGame> gameUpdatePosts;
	
	public enum ApprovalStatus {
		APPLIED, UPDATED, APPROVED, REJECTED
	}	
	   
	    public Application() {
		setVisibilityStatus(false);
		this.approvalStatus = ApprovalStatus.APPLIED;
	}
}
