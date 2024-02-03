package sg.edu.nus.iss.gamerecommender.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Entity
@Data
public class ProfileGame extends Profile {
		
	@Enumerated(EnumType.STRING)
	private ApprovalStatus approvalStatus;
	
	// private List<GameUpdatePost> gameUpdatePosts;
	
	// private List<GameReviewPost> gameReviewPosts;
	
	public enum ApprovalStatus {
		APPLIED, UPDATED, APPROVED, REJECTED
	}
	
	public ProfileGame() {
		super.setVisibilityStatus(true);
		this.approvalStatus = ApprovalStatus.APPLIED;
		// this.gameUpdatePosts = new ArrayList<>();
		// this.gameReviewPosts = new ArrayList<>();
	}
}
