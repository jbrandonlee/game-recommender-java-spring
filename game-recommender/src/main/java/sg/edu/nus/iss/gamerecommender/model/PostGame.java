package sg.edu.nus.iss.gamerecommender.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PostGame extends Post {

	@ManyToOne
	@JsonBackReference
	private ProfileGame gameProfile;
	
	public PostGame(String title, String message, Profile userProfile, ProfileGame gameProfile) {
		super(title, message, userProfile);
		this.gameProfile = gameProfile;
	}
}
