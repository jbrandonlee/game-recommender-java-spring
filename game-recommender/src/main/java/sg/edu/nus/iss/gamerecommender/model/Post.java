package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	
	public String title;
	
	@Column(columnDefinition="TEXT")
	private String message;
	
	@CreationTimestamp
	private LocalDate datePosted;
	
	@ManyToOne
	@JsonBackReference
	private Profile userProfile;

	
	@JsonProperty("userDisplayname")
	public String getUserDisplayName() {
		if (userProfile instanceof ProfileGamer) {
			return ((ProfileGamer)userProfile).getUser().getDisplayName();
		} else if (userProfile instanceof ProfileDeveloper) {
				return ((ProfileDeveloper)userProfile).getUser().getDisplayName();
		}
		return null;
	}
	
	@JsonProperty("userId")
	public int getUserId() {
		if (userProfile instanceof ProfileGamer) {
			return ((ProfileGamer)userProfile).getUser().getId();
		} else if (userProfile instanceof ProfileDeveloper) {
				return ((ProfileDeveloper)userProfile).getUser().getId();
		}
		return 0;
	}
	
	@JsonProperty("userImageUrl")
	public String getUserImageUrl() {
		if (userProfile instanceof ProfileGamer) {
			return ((ProfileGamer)userProfile).getUser().getDisplayImageUrl();
		} else if (userProfile instanceof ProfileDeveloper) {
				return ((ProfileDeveloper)userProfile).getUser().getDisplayImageUrl();
		}
		return null;
	}
	
	public Post(String title, String message, Profile userProfile) {
		this.title = title;
		this.message = message;
		this.datePosted = LocalDate.now();
		this.userProfile = userProfile;
	}
}