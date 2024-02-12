package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.GameApplication;
import sg.edu.nus.iss.gamerecommender.model.GameApplication.ApprovalStatus;

public interface GameApplicationRepository extends JpaRepository<GameApplication, Integer>  {
	@Query("SELECT g FROM GameApplication g WHERE g.developer.id=:devId")
	public List<GameApplication> findAllByDevId(@Param("devId") int devId);
	
	@Query("SELECT g FROM GameApplication g WHERE g.developer.id=:devId AND g.approvalStatus IN ('APPLIED', 'UPDATED')")
	public List<GameApplication> findPendingByDevId(@Param("devId") int devId);
	
	@Query("SELECT g FROM GameApplication g WHERE g.developer.id=:devId AND g.approvalStatus=:status")
	public List<GameApplication> findByDevIdAndStatus(@Param("devId") int devId, @Param("status") ApprovalStatus status);
}
