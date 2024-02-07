package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ProfileGame extends Profile {
	
	@OneToOne(mappedBy="profile")
	private Game game;
	
	@Enumerated(EnumType.STRING)
	private ApprovalStatus approvalStatus;

	@OneToMany(mappedBy="gameProfile")
	private List<PostGame> gameUpdatePosts;
	
	@OneToMany(mappedBy="gameProfile")
	private List<PostGameReview> gameReviewPosts;
	
	public enum ApprovalStatus {
		APPLIED, UPDATED, APPROVED, REJECTED,PUBLISHED
	}

	@Override
	public String toString() {
		return "ProfileGame{" + "game=" + game + ", approvalStatus=" + approvalStatus + ", gameUpdatePosts=" + gameUpdatePosts + ", gameReviewPosts=" + gameReviewPosts + '}';
	}

	public ProfileGame() {
		super.setVisibilityStatus(true);
		this.approvalStatus = ApprovalStatus.APPLIED;
	}
}
