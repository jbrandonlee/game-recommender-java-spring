package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Account;

public interface AccountRepository extends JpaRepository<Account, String>  {
	@Query("SELECT a FROM Account a WHERE a.username=:username AND a.password=:password")
	public Account findAccountByUsernamePassword(@Param("username") String username, @Param("password") String password);
	
	@Query("SELECT a FROM Account a WHERE a.role=:role")
	public List<Account> findAccountsByRole(@Param("role") String role);
	
	@Query("SELECT Count(a) FROM Account a WHERE a.role=:role")
	public int findAccountCountByRole(@Param("role") String role);
}