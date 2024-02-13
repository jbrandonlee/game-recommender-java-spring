package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.iss.gamerecommender.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{

}
