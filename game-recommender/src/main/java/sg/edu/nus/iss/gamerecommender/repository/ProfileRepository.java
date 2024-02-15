package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Profile;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
//	@Query("SELECT p from User u JOIN u.profile p WHERE u.displayName=:name")
//	public Profile findProfileByUserDisplayName(@Param("name") String displayName);
//	
//	@Query("SELECT p from Game g JOIN g.profile p WHERE g.title=:title")
//	public ProfileGame findProfileByGameTitle(@Param("title") String title);
	@Query("SELECT p from Game g JOIN g.profile p WHERE g.title=:title")
	public ProfileGame findProfileByGameTitle(@Param("title") String title);
	
	@Query("SELECT p from User u JOIN u.profile p WHERE u.id=:userId")
	public Profile findProfileByUserId(@Param("userId") int userId);
	
	@Query("SELECT p from User u JOIN u.profile p WHERE u.id=:id")
	public Profile findProfileByUserId(@Param("id") String userId);
	
	@Query("SELECT p from Game g JOIN g.profile p WHERE g.id=:id")
	public ProfileGame findProfileByGameId(@Param("id") String gameId);
}
