package sg.edu.nus.iss.gamerecommender.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Profile {
	@Id
	private int id;
	private String displayName;
	private String displayImageUrl;
	private VisibilityStatus visibility;
	private ApprovalStatus approvalStatus;
	
	public enum VisibilityStatus {
		VISIBLE, HIDDEN
	}
	
	public enum ApprovalStatus {
		PENDING, UPDATED, APPROVED, REJECTED
	}
}