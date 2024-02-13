package sg.edu.nus.iss.gamerecommender.service;

public interface DashboardService {
	
	long getTotalGamers();
    long getTotalDevelopers();
    long getTotalGames();
    long getGamesPendingReview();

    long getTotalAccountGame(String name);

    double getAvgAccountGame(String name);
}
