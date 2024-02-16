package sg.edu.nus.iss.gamerecommender.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sg.edu.nus.iss.gamerecommender.interceptor.SecurityInterceptor;

@Component
public class WebAppConfig implements WebMvcConfigurer{
  @Autowired
  SecurityInterceptor securityInterceptor;
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(securityInterceptor);
  }
}
