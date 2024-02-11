package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	
	@Query("SELECT a FROM Activity a WHERE ((a.parentType = 'USER') AND (a.parentId = :userId)) ORDER BY a.timeCreated DESC")
	public List<Activity> findUserActivity(@Param("userId") int gameId);
	
	@Query("SELECT a FROM Activity a "
			+ "WHERE ((a.parentType = 'USER') AND (a.parentId = :userId)) "														// SELECT own posts
			+ "OR ((a.parentType = 'USER') AND (a.parentId IN (SELECT f.id FROM User u JOIN u.profile.friends f))) "			// SELECT friend post
			+ "OR ((a.parentType = 'USER') AND (a.parentId IN (SELECT d.id FROM User u JOIN u.profile.followedDevelopers d)))"	// SELECT followedDev post
			+ "OR ((a.parentType = 'GAME') AND (a.parentId IN (SELECT g.id FROM User u JOIN u.profile.followedGames g)))"		// SELECT followedGame post
			+ "ORDER BY a.timeCreated DESC")
	public Page<Activity> findUserActivityPaged(@Param("userId") int gameId, Pageable pageable);

}