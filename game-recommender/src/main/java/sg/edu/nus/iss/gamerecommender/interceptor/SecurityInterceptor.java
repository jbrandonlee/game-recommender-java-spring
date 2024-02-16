package sg.edu.nus.iss.gamerecommender.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, Object handler) 
							 throws IOException {
		
	    String uri = request.getRequestURI();
	    if (uri.startsWith("/css/") || uri.startsWith("/image/")) {
	      return true;
	    }
	    
	    if (uri.equalsIgnoreCase("/") || uri.equalsIgnoreCase("/home") 
	    	|| uri.equalsIgnoreCase("/login") || uri.equalsIgnoreCase("/authenticate")
	        || uri.startsWith("/register") || uri.startsWith("/search")) {
	      return true;
	    }
	    
	    HttpSession sessionObj = request.getSession();
		User user = (User) sessionObj.getAttribute("user");		
		if (user == null) {
			response.sendRedirect("/login");
			return false;
		}
		
		if (uri.startsWith("/admin") && user.getRole() != Role.ADMIN) {
	    	response.sendRedirect("/");
	    }
		
		if (uri.startsWith("/dev") && user.getRole() != Role.DEVELOPER) {
	    	response.sendRedirect("/");
	    }
		
		if (!uri.startsWith("/user/profile") && uri.startsWith("/user") && user.getRole() != Role.GAMER) {
	    	response.sendRedirect("/");
	    }
		
		return true;
	}
	 
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}
	 
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response, Object handler, Exception ex) {
	}
}
