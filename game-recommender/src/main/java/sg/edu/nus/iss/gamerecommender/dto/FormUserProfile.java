package sg.edu.nus.iss.gamerecommender.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormUserProfile {
	private String displayName;
	private String displayImageUrl;
	private String biography;
	private boolean visibilityStatus;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayImageUrl() {
		return displayImageUrl;
	}
	public void setDisplayImageUrl(String displayImageUrl) {
		this.displayImageUrl = displayImageUrl;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public boolean isVisibilityStatus() {
		return visibilityStatus;
	}
	public void setVisibilityStatus(boolean visibilityStatus) {
		this.visibilityStatus = visibilityStatus;
	}
}
