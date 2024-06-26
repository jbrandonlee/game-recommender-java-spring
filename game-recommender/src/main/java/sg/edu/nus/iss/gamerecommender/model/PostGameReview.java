package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
	
	public PostGameReview(String title, String message, Profile userProfile, ProfileGame gameProfile, boolean isRecommend, LocalDate datePosted) {
		super(title, message, userProfile);
		super.setDatePosted(datePosted);
		this.gameProfile = gameProfile;
		this.isRecommend = isRecommend;
	}

}
