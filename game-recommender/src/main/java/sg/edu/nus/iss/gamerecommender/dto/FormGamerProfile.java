package sg.edu.nus.iss.gamerecommender.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;

@Data
@NoArgsConstructor
public class FormGamerProfile {
	private String displayName;
	private String displayImageUrl;
	private String biography;
	private List<Genre> genrePreferences;
	private boolean visibilityStatus;
}
