package sg.edu.nus.iss.gamerecommender;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Account.Role;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.Game.Platform;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.repository.AccountRepository;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;
import sg.edu.nus.iss.gamerecommender.repository.UserRepository;

@SpringBootApplication
public class GameRecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameRecommenderApplication.class, args);
	}
	
	@Bean
    CommandLineRunner loadData(UserRepository userRepo, AccountRepository accountRepo, GameRepository gameRepo) {
		return args -> {
			
			User admin = userRepo.save(new User());
			User dev = userRepo.save(new User());
			
			accountRepo.save(new Account("admin", "password1", Role.ADMIN, admin));
			accountRepo.save(new Account("dev", "password1", Role.DEVELOPER, dev));
			
			gameRepo.save(new Game(440, "Team Fortress 2", "One of the most popular online action games of all time...",
					LocalDate.of(2007, 10,10), 0.00, 0.935,
					"https://cdn.akamai.steamstatic.com/steam/apps/440/header.jpg?t=1592263852",
					"http://www.teamfortress.com/",
					List.of(Platform.WINDOWS, Platform.MAC),
					"Valve",
					List.of("Cats", "OtherCats"),
					List.of(Genre.ACTION)));			
		};
	}
}
