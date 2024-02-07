package sg.edu.nus.iss.gamerecommender.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ApprovedProfileGame extends Profile {

	@OneToOne(mappedBy="profile")
	private Game game;


	@OneToMany(mappedBy="gameProfile")
	private List<PostGame> gameUpdatePosts;

	@OneToMany(mappedBy="gameProfile")
	private List<PostGameReview> gameReviewPosts;
	
	@Override
	public String toString() {
		return "ProfileGame{" + "game=" + game +  ", gameUpdatePosts=" + gameUpdatePosts + ", gameReviewPosts=" + gameReviewPosts + '}';
	}

	public ApprovedProfileGame() {
		super.setVisibilityStatus(true);
	}
}
