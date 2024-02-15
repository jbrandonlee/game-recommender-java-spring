package sg.edu.nus.iss.gamerecommender.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.gamerecommender.model.Game;

@Service
public class RecommenderService {
	
	@Autowired
	GameService gameService;
	
	public static final String BASE_URL = "http://localhost:8888";
	
	public List<Game> getRelatedGames(int gameId, int size, boolean shuffle) {
		List<Game> gameList = new ArrayList<Game>();
		
		try {
			// Create Connection
			URL url = new URL(BASE_URL + "/api/ml/recommendations/game" + "?id=" + gameId + "&size=" + size);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			// Get Results
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			
			// Parse Results
			String result = content.toString();
			result = result.replaceAll("[\\[\\]\\,]", "");
			List<String> idList = Arrays.asList(result.split(" "));
			
			// Get Game Objects
			gameList = gameService.findGamesFromIdList(idList);
			if (shuffle) { Collections.shuffle(gameList); }
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return gameList;
	}
	
	public List<Game> getUserRecommendations(int userId, int size, boolean shuffle) {
		List<Game> gameList = new ArrayList<Game>();
		
		try {
			// Create Connection
			URL url = new URL(BASE_URL + "/api/ml/recommendations/user" + "?id=" + userId + "&size=" + size);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			// Get Results
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			
			// Parse Results
			String result = content.toString();
			result = result.replaceAll("[\\[\\]\\,]", "");
			List<String> idList = Arrays.asList(result.split(" "));
			
			// Get Game Objects
			gameList = gameService.findGamesFromIdList(idList);
			if (shuffle) { Collections.shuffle(gameList); }
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
//		if (gameList.isEmpty()) {
//			gameList = new ArrayList<>(gameService.findTopRated(1, 10).getContent());
//			Collections.shuffle(gameList);
//			gameList = gameList.subList(0, Math.min(size, gameList.size()));
//		}
		
		return gameList;
	}
}
