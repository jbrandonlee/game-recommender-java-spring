package sg.edu.nus.iss.gamerecommender.model;

import java.time.LocalDate;

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
	
	private LocalDate datePosted;
	
	@ManyToOne
	private Profile userProfile;
	
	public Post(String title, String message, Profile userProfile) {
		this.title = title;
		this.message = message;
		this.datePosted = LocalDate.now();
		this.userProfile = userProfile;
	}
}