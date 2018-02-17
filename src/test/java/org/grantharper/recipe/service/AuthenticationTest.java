package org.grantharper.recipe.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationTest
{

  @Test
  public void testEncoding()
  {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    String password = "test";
    String encodedPassword = encoder.encode(password);
    System.out.println(encodedPassword);
    
    assertTrue(encoder.matches(password, encodedPassword));
    
  }

}
