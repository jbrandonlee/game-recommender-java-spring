package sg.edu.nus.iss.gamerecommender.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import sg.edu.nus.iss.gamerecommender.model.ApprovedGame;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;

import java.util.List;

public interface ApprovedGameService {
    List<ApprovedGame> findGamesByDevId(int id);

//    ApprovedGame applyGame(ApprovedGame leaveApplication);
    
    ApprovedGame applyGame(ApprovedGame game);

    List<ApprovedGame> findAllLeaveApplication();

    ApprovedGame findById(Integer id);

    ApprovedGame editGame(ApprovedGame approvedgame);

    void removeGame(Integer id);

    Page<ApprovedGame> findGamePage(Integer accountId, int pageNo, int pageSize, String sortField, String sortDirection);

    Long getGameCount(List<ProfileGame.ApprovalStatus> status, int accountId);
    

}
