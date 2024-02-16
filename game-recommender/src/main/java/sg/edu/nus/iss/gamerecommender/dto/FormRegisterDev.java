package sg.edu.nus.iss.gamerecommender.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormRegisterDev {
	private String username;
	private String password;
	private String displayName;
}
