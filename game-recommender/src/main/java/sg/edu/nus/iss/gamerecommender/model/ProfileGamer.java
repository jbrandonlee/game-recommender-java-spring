package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;

@Data
@Entity
public class ProfileGamer extends Profile {
	
	@OneToOne(mappedBy="profile")
	@JsonBackReference
	private User user;
	
	@OneToMany(mappedBy="userProfile")
	@JsonManagedReference
	private List<Post> gamerPosts;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Genre.class)
	private List<Genre> genrePreferences;			// To show on User Profile
	
	@ManyToMany
	@JsonBackReference
	private List<User> friends;						// To View User(Friend) Profile
	
	@ManyToMany
	@JsonBackReference
	private List<User> followedDevelopers;			// To View User(Dev) Profile, Dev Posts

	@ManyToMany
	@JsonBackReference
	private List<Game> followedGames;				// To View Game Page, Price, Game Updates	(Replaces Wishlist/Favourites)
	
	//@ManyToMany
	//private List<GameList> gameLists;			// To Create Custom Game Lists
	
	//@OneToMany(mappedBy="user")
	//private List<Activity> activities;		// To Track User Activities, which are 'Posts' made by Users
	
	public ProfileGamer() {
		super.setVisibilityStatus(true);
	}

	public void addFriend(User friend) {
		this.friends.add(friend);
	}

	public void removeFriend(User friend) {
		this.friends.remove(friend);
	}
	
	public void addFollowedDev(User dev) {
		this.followedDevelopers.add(dev);
	}
	
	public void removeFollowedDev(User dev) {
		this.followedDevelopers.remove(dev);
	}
	
	public void addFollowedGame(Game game) {
		this.followedGames.add(game);
	}
	
	public void removeFollowedGame(Game game) {
		this.followedGames.remove(game);
	}
}
