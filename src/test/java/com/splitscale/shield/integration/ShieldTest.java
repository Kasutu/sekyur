package com.splitscale.shield.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.splitscale.shield.Shield;
import com.splitscale.shield.validate.ValidJwtResponse;
import com.splitscale.shield.login.LoginResponse;
import com.splitscale.shield.user.UserRequest;
import com.splitscale.shield.user.repository.ObjectNotFoundException;

public class ShieldTest {

  @BeforeAll
  static void setup() {
    Shield.initialize();
  }

  @Test
  public void testRegisterUser() throws InvalidKeyException, IOException {
    // Prepare test data
    UserRequest request = new UserRequest("joejoe", "password");

    // Verify that no exceptions are thrown
    assertDoesNotThrow(() -> Shield.registerUser(request));
  }

  @Test
  public void testLoginUser() throws InvalidKeyException, IOException, ObjectNotFoundException {
    // Prepare test data
    UserRequest request = new UserRequest("joejoe", "password");

    // Verify that no exceptions are thrown
    assertDoesNotThrow(() -> System.out.println(Shield.loginUser(request).getToken()));
  }

  @Test
  public void testValidateJwt() throws IOException, ObjectNotFoundException, GeneralSecurityException {
    // Prepare test data
    UserRequest request = new UserRequest("joejoe", "password");

    LoginResponse response = Shield.loginUser(request);

    // Verify that no exceptions are thrown
    ValidJwtResponse validJwt = Shield.validateJwt(response.getToken(), response.getUserResponse().getId());
    assertNotNull(validJwt);

    System.out.println(validJwt.getToken());

    System.out.println(validJwt.getClaims().getId());
    System.out.println(validJwt.getClaims().getAudience());
    System.out.println(validJwt.getClaims().getIssuer());
  }

  @Test
  public void testInValidateJwt()
      throws InvalidKeyException, IOException, ObjectNotFoundException, GeneralSecurityException {
    // Prepare test data
    UserRequest request = new UserRequest("joejoe", "password");

    LoginResponse response = Shield.loginUser(request);

    String invalidJwt = Shield.inValidateJwt(response.getToken());
    assertNotNull(invalidJwt);

    System.out.println("Invalid Jwt: " + invalidJwt);
  }
}