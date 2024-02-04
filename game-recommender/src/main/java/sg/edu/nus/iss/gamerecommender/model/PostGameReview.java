package sg.edu.nus.iss.gamerecommender.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PostGameReview extends PostGame {
	
	public boolean isRecommend;
	
	public PostGameReview(String title, String message, Profile userProfile, ProfileGame gameProfile, boolean isRecommend) {
		super(title, message, userProfile, gameProfile);
		this.isRecommend = isRecommend;
	}
}
