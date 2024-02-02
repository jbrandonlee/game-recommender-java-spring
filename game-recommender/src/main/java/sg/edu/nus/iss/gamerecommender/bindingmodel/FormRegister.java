package sg.edu.nus.iss.gamerecommender.bindingmodel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormRegister {
	private String username;
	private String password;
	private String displayName;
}
