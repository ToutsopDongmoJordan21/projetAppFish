package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class ProjetAppFishApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void loginWithValidUserThenAuthenticated() throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// "$2a$10$nprnJwctuGQ7FC.NF/nfB.31b3XpY6HaMXKu5sWi56aDnkXA6zitC"
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
				.user("flobert11")
				.password("123456");

		mockMvc.perform(login).andExpect(authenticated().withUsername("flobert11"));
	}

	@Test
	public void loginWithInvalidUserThenUnauthenticated() throws Exception {
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
				.user("invalid")
				.password("invalidpassword");

		mockMvc.perform(login)
				.andExpect(unauthenticated());
	}

	@Test
	public void accessUnsecuredResourceThenOk() throws Exception {
		mockMvc.perform(get("/css/style.css"))
				.andExpect(status().isOk());
	}

	@Test
	public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser
	public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
		mockMvc.perform(get("/index"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = "USER")
	public void loginWithRoleUserThenExpectAdminPageForbidden() throws Exception {
		mockMvc.perform(get("/admin"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void loginWithRoleAdminThenExpectAdminContent() throws Exception {
		mockMvc.perform(get("/admin"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Custom administrator page.")));
	}

	@Test
	public void loginWithRoleUserThenExpectIndexPageRedirect() throws Exception {
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
				.user("user")
				.password("pass");

		mockMvc.perform(login)
				.andExpect(authenticated().withUsername("user"))
				.andExpect(redirectedUrl("/index"));
	}

	@Test
	public void loginWithRoleAdminThenExpectAdminPageRedirect() throws Exception {
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
				.user("admin")
				.password("pass");

		mockMvc.perform(login)
				.andExpect(authenticated().withUsername("admin"))
				.andExpect(redirectedUrl("/admin"));
	}

}


