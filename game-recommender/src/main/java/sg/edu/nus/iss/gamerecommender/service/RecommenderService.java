package sg.edu.nus.iss.gamerecommender.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class RecommenderService {
	
	public String getRelatedGameIds(Integer gameId) {
		try {
			URL url = new URL("http://localhost:8888/api/ml/recommendations/" + "?id=" + gameId + "&start=0&end=10");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			return content.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
