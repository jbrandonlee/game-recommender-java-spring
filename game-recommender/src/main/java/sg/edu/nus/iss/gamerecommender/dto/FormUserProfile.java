package sg.edu.nus.iss.gamerecommender.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormUserProfile {
	private String displayName;
	private String displayImageUrl;
	private String biography;
	private boolean visibilityStatus;
}
