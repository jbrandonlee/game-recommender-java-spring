package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.GameApplication;
import sg.edu.nus.iss.gamerecommender.model.GameApplication.ApprovalStatus;

public interface GameApplicationRepository extends JpaRepository<GameApplication, Integer>  {
	
	@Query("SELECT g FROM GameApplication g WHERE g.id=:id AND g.developer.id=:devId")
	public GameApplication findByIdAndDevId(@Param("id") int id, @Param("devId") int devId);
	
	@Query("SELECT g FROM GameApplication g WHERE g.developer.id=:devId")
	public List<GameApplication> findAllByDevId(@Param("devId") int devId);
	
	@Query("SELECT COUNT(g) FROM GameApplication g WHERE g.approvalStatus IN ('APPLIED', 'UPDATED')")
	public Integer countAllPending();		// For Admin
	
	@Query("SELECT g FROM GameApplication g WHERE g.approvalStatus IN ('APPLIED', 'UPDATED')")
	public List<GameApplication> findAllPending();		// For Admin
	
	@Query("SELECT COUNT(g) FROM GameApplication g WHERE g.developer.id=:devId AND g.approvalStatus IN ('APPLIED', 'UPDATED', 'APPROVED')")
	public Integer countPendingByDevId(@Param("devId") int devId);		// For Dev
	
	@Query("SELECT g FROM GameApplication g WHERE g.developer.id=:devId AND g.approvalStatus IN ('APPLIED', 'UPDATED', 'APPROVED')")
	public List<GameApplication> findPendingByDevId(@Param("devId") int devId);		// For Dev
	
	@Query("SELECT g FROM GameApplication g WHERE g.gameId=:gameId AND g.approvalStatus IN ('APPLIED', 'UPDATED', 'APPROVED')")
	public GameApplication findPendingByGameId(@Param("gameId") int gameId);		// For Dev

	@Query("SELECT g FROM GameApplication g WHERE g.developer.id=:devId AND g.approvalStatus=:status")
	public List<GameApplication> findByDevIdAndStatus(@Param("devId") int devId, @Param("status") ApprovalStatus status);
}
