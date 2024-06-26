package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ProfileDeveloper extends Profile {
	
	@OneToOne(mappedBy="profile")
	@JsonBackReference
	private User user;
	
	@OneToMany(mappedBy="userProfile")
	@JsonManagedReference
	private List<Post> devBlogPosts;
		
	public ProfileDeveloper() {
		super.setVisibilityStatus(true);
	}
}