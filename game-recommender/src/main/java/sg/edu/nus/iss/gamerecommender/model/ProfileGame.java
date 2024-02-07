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
	
	@OneToMany(mappedBy="gameProfile")
	private Application application;
	
	public enum ApprovalStatus {
		APPLIED, UPDATED, APPROVED, REJECTED
	}
	public ProfileGame() {
		super.setVisibilityStatus(true);
		this.approvalStatus = ApprovalStatus.APPLIED;
	}
}
