package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
	
	@Query("SELECT g FROM Game g JOIN g.developer d WHERE g.developer.id=:id")
	public List<Game> findGamesByDevId(@Param("id") int devId);
	
	@Query("SELECT g FROM Game g WHERE g.id=:id")
	public Game findGameById(@Param("id") int gameId);
	
	@Query("SELECT g FROM Game g ORDER BY g.rating DESC")
	public List<Game> findAllSortedTopRating();
	
	@Query("SELECT g FROM Game g WHERE g.title LIKE CONCAT('%',:query,'%')")
	public List<Game> searchGames(@Param("query")String query);
}
