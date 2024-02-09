package sg.edu.nus.iss.gamerecommender.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PostGameReview extends Post {
	
	@ManyToOne
	@JsonBackReference
	private ProfileGame gameProfile;
	
	public Boolean isRecommend;
	
	public PostGameReview(String title, String message, Profile userProfile, ProfileGame gameProfile, boolean isRecommend) {
		super(title, message, userProfile);
		this.gameProfile = gameProfile;
		this.isRecommend = isRecommend;
	}

}
