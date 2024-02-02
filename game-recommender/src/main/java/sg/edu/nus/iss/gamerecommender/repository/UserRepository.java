package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {	
	@Query("SELECT u FROM Account a JOIN a.user u WHERE a.username=:username")
	public User findUserByAccountUsername(@Param("username")String username);
}
