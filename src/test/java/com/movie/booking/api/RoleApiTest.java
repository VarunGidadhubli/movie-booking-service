package com.movie.booking.api;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.booking.testconstants.TestConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RoleApiTest {

	ObjectMapper objectMapper = new ObjectMapper();

	//The following test should return unauthorized since only an admin is allowed to create role
	@Test(priority = 1)
	public void createRoleWithoutAuthentication() throws JsonProcessingException {

		RoleRequestDTO dto = new RoleRequestDTO();
		dto.setName("MANAGER");
		String requestBody = objectMapper.writeValueAsString(dto);

		Response response = RestAssured. given().
								baseUri(TestConstants.BASE_URL).
									contentType(ContentType.JSON).
										body(requestBody).
											when()
												.post("/api/roles");
		
		Assert.assertEquals(response.statusCode(),TestConstants.UNAUTHORIZED,"Invalid Status code");
	}
	
	//The following test should return forbidden since only an admin is allowed to create role
	@Test(priority = 2)
	public void createRoleWithAuthentication() throws JsonProcessingException {
		
		String username="TestUser", password="SecurePass123!", mail="test@email.com";
		
		UserRequestDTO dto = new UserRequestDTO();
		dto.setUsername(username);
		dto.setPassword(password);
		dto.setEmail(mail);
		
		String createUserRequestBody = objectMapper.writeValueAsString(dto);
		
		Response createUserResponse =
				RestAssured.given().baseUri(TestConstants.BASE_URL)
				.contentType(ContentType.JSON)
				.body(createUserRequestBody)
				.when()
				.post("/api/user");
		
		Assert.assertEquals(createUserResponse.statusCode(),TestConstants.CREATED,"Record not created");
		Assert.assertEquals(createUserResponse.jsonPath().getString("username"), username,"Username is mismatched");
		
		//Login as the above user
		LoginRequestDTO loginDto = new LoginRequestDTO();
		loginDto.setEmail(mail);
		loginDto.setPassword(password);
		
		String loginRequestBody = objectMapper.writeValueAsString(loginDto);
		
		Response loginResponse = RestAssured
		.given().baseUri(TestConstants.BASE_URL)
		.contentType(ContentType.JSON)
		.body(loginRequestBody)
		.when()
		.post("/api/auth/login");
		
		LoginResponseDTO loginResponseDTO = loginResponse.as(LoginResponseDTO.class);
		Assert.assertEquals(loginResponse.statusCode(), 200);
		Assert.assertNotNull(loginResponseDTO.getAccessToken(), "Access token is empty");
		Assert.assertNotNull(loginResponseDTO.getTokenType(), "Token type is empty");
		Assert.assertEquals(loginResponseDTO.getTokenType(), "Bearer");
		
		// Use the JWT received during login to create a role.
		// Since this user is not an admin, the request should return 403.
		RoleRequestDTO roleRequestDto = new RoleRequestDTO();
		roleRequestDto.setName("MANAGER");
		String roleRequestBody = objectMapper.writeValueAsString(roleRequestDto);

		Response response = RestAssured. given().
								baseUri(TestConstants.BASE_URL).
									contentType(ContentType.JSON).
										header("Authorization",
												loginResponseDTO.getTokenType() + " " + loginResponseDTO.getAccessToken())
											.body(roleRequestBody).
												when()
													.post("/api/roles");
		
		Assert.assertEquals(response.statusCode(),TestConstants.FORBIDDEN,"Creation of record should be forbidden");
		
	}
	
	@Test(priority = 3)
	public void createRoleWithAuthorizedUser() throws JsonProcessingException {
		
		String mail="alice@example.com", password="SecurePass123!";
		
		LoginRequestDTO loginDto = new LoginRequestDTO();
		loginDto.setEmail(mail);
		loginDto.setPassword(password);
		
		String loginRequestBody = objectMapper.writeValueAsString(loginDto);
		
		Response loginResponse = RestAssured
		.given().baseUri(TestConstants.BASE_URL)
		.contentType(ContentType.JSON)
		.body(loginRequestBody)
		.when()
		.post("/api/auth/login");
		
		LoginResponseDTO loginResponseDTO = loginResponse.as(LoginResponseDTO.class);
		Assert.assertEquals(loginResponse.statusCode(), 200);
		Assert.assertNotNull(loginResponseDTO.getAccessToken(), "Access token is empty");
		Assert.assertNotNull(loginResponseDTO.getTokenType(), "Token type is empty");
		Assert.assertEquals(loginResponseDTO.getTokenType(), "Bearer");
		
		// Use the JWT received during login to create a role.
		RoleRequestDTO roleRequestDto = new RoleRequestDTO();
		roleRequestDto.setName("MANAGER");
		String roleRequestBody = objectMapper.writeValueAsString(roleRequestDto);

		Response response = RestAssured. given().
								baseUri(TestConstants.BASE_URL).
									contentType(ContentType.JSON).
										header("Authorization",
												loginResponseDTO.getTokenType() + " " + loginResponseDTO.getAccessToken())
											.body(roleRequestBody).
												when()
													.post("/api/roles");
		
		Assert.assertEquals(response.statusCode(),TestConstants.CREATED,"Record should be created");
		
	}
	
	@Test(priority = 4)
	public void getAllRoles() throws JsonProcessingException {
		
		String mail = "alice@example.com", password = "SecurePass123!";

		LoginRequestDTO loginDto = new LoginRequestDTO();
		loginDto.setEmail(mail);
		loginDto.setPassword(password);

		String loginRequestBody = objectMapper.writeValueAsString(loginDto);

		Response loginResponse = RestAssured.given().baseUri(TestConstants.BASE_URL).contentType(ContentType.JSON)
				.body(loginRequestBody).when().post("/api/auth/login");

		LoginResponseDTO loginResponseDTO = loginResponse.as(LoginResponseDTO.class);
		Assert.assertEquals(loginResponse.statusCode(), 200);
		Assert.assertNotNull(loginResponseDTO.getAccessToken(), "Access token is empty");
		Assert.assertNotNull(loginResponseDTO.getTokenType(), "Token type is empty");
		Assert.assertEquals(loginResponseDTO.getTokenType(), "Bearer");
		
		Response listOfRoles = 
							RestAssured.given()
								.baseUri(TestConstants.BASE_URL)
								.header("Authorization",loginResponseDTO.getTokenType() + " " + loginResponseDTO.getAccessToken())
								.when()
								.get("/api/roles");
		
		List<RoleResponseDTO> roles = listOfRoles.as(new io.restassured.common.mapper.TypeRef<List<RoleResponseDTO>>() {});
		Assert.assertFalse(roles.isEmpty(), "No roles are found");
		
		for(RoleResponseDTO roleResponseDTO : roles) {
			
			Assert.assertNotNull(roleResponseDTO.getId(), "Role ID is null");
		    Assert.assertNotNull(roleResponseDTO.getName(), "Role name is null");
			System.out.println(roleResponseDTO.getId()+" "+roleResponseDTO.getName());
		}
	}
}