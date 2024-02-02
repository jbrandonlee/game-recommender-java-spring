package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;

public class ProfileGamer extends Profile {
	
	private String biography;	
	
	@OneToMany(mappedBy="user")
	private List<Genre> genrePreferences;		// To show on User Profile
	
	@ManyToMany
	private List<User> friends;					// To View User(Friend) Profile
	
	@ManyToMany
	private List<User> followedDevelopers;		// To View User(Dev) Profile, Dev Posts

	@ManyToMany
	private List<Game> followedGames;			// To View Game Page, Price, Game Updates	(Replaces Wishlist/Favourites)
	
	//@ManyToMany
	//private List<GameList> gameLists;			// To Create Custom Game Lists
	
	//@OneToMany(mappedBy="user")
	//private List<Activity> activities;		// To Track User Activities, which are 'Posts' made by Users

}
