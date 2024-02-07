package sg.edu.nus.iss.gamerecommender.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;

import java.util.List;

public interface GameService {
    List<Game> findGamesByDevId(int id);

    Game applyGame(Game leaveApplication);

    List<Game> findAllLeaveApplication();

    Game findById(Integer id);

    Game editGame(Game game);

    void removeGame(Integer id);

    Page<Game> findGamePage(Integer accountId, int pageNo, int pageSize, String sortField, String sortDirection);

    Long getGameCount(List<ProfileGame.ApprovalStatus> status, int accountId);
    
//    void checkAndSaveApprovedGame(Game game);

}
