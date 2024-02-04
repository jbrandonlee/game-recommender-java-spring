package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
	
	@Query("SELECT g FROM Game g JOIN g.developer d WHERE g.developer.id=:id")
	public List<Game> findGamesByDevId(@Param("id") int devId);
	
	//@Query("SELECT g FROM Game g WHERE g.title=:title")
	//public Game findGameByGameTitle(@Param("id") String title);

}
