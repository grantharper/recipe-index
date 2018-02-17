package org.grantharper.recipe.service;

import java.util.Arrays;
import java.util.List;

import org.grantharper.recipe.model.RecipeUser;
import org.grantharper.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RecipeUserDetailsService implements UserDetailsService
{
  
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    try {
      RecipeUser user = userRepository.findByUsername(username);
      if (user == null) {
          throw new UsernameNotFoundException("No user found with username: " + username);
      }
      User userDetails = new User(user.getUsername(), user.getPassword(), true, true, true, true, getAuthorities());
      return userDetails;
  } catch (final Exception e) {
      throw new RuntimeException(e);
  }
  }
  
  public List<GrantedAuthority> getAuthorities()
  {
    return Arrays.asList(new SimpleGrantedAuthority("USER"));
  }

}
