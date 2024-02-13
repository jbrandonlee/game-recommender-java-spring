package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ProfileGame extends Profile {
	
	@OneToOne(mappedBy="profile")
	@JsonBackReference
	private Game game;
		
	@OneToMany(mappedBy="gameProfile")
	@JsonManagedReference
	private List<PostGame> gameUpdatePosts;
	
	@OneToMany(mappedBy="gameProfile")
	@JsonManagedReference
	private List<PostGameReview> gameReviewPosts;
	
	public ProfileGame() {
		super.setVisibilityStatus(true);
	}
}
