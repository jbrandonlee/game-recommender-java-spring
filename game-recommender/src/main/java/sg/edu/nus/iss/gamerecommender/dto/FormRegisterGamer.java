package sg.edu.nus.iss.gamerecommender.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;

@Data
@NoArgsConstructor
public class FormRegisterGamer {
	private String username;
	private String password;
	private String displayName;
	private List<Genre> genrePreferences;
}
