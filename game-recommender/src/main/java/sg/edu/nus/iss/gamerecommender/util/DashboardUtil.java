package sg.edu.nus.iss.gamerecommender.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class DashboardUtil {
	
	public static List<String> getPastWeekDayNamesFromNow() {
		List<String> pastWeekDayNames = new ArrayList<>();
		for (int i = 6; i >= 0; i--) {
			DayOfWeek dayOfWeek = LocalDate.now().minusDays(i).getDayOfWeek();
			pastWeekDayNames.add(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
		}
		return pastWeekDayNames;
	}
	
}
