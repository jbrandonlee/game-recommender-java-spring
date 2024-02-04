package sg.edu.nus.iss.gamerecommender.model;

import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

public class GameList {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private GameListType listType;
	
	@ManyToMany
	private List<Game> gameList;
	
	public enum GameListType {
		FAVOURITE, WISHLIST, FOLLOWED, OTHER
	}
}