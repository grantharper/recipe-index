package org.grantharper.recipe.config;

import org.grantharper.recipe.service.RecipeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile({"dev"})
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter
{
  @Autowired
  RecipeUserDetailsService userDetailsService;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.authorizeRequests().antMatchers("/console/**", "/**").permitAll()
        .antMatchers("/health", "/webjars/**", "/css/**", "/js/**", "/img/**", "/favicon.png").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll().and()
        .logout().logoutSuccessUrl("/login?logout").permitAll();
    
    // disable these protections so that I can access the H2 console
    httpSecurity.csrf().disable();
    httpSecurity.headers().frameOptions().disable();
    
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(encoder());
      return authProvider;
  }

  @Bean
  public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder();
  }
  
    
}
