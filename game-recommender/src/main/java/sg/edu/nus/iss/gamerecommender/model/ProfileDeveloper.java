package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class ProfileDeveloper extends Profile {
	
	@OneToMany(mappedBy="userProfile")
	private List<Post> devBlogPosts;
	
	// private List<Game> devGames;
	
}