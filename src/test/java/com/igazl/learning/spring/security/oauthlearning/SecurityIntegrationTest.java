package com.igazl.learning.spring.security.oauthlearning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

//    @Autowired
//    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


//    This won't work as this service is not Reactive
//    Even with @WebfluxTest annotation because there are no Webflux realted configurers.
//    @Test
//    void test_WebClient_UserShouldReturnOkWhenRequestHasLearningUserRole() throws Exception {
//        webTestClient
//                .get()
//                .uri("/test/user")
//                .exchange()
//                .expectStatus().isOk();
//    }

    @Test
    void testUserShouldReturnOkWhenRequestHasLearningUserRole() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get("/test/user")
                        .with(
                                SecurityMockMvcRequestPostProcessors.jwt()
                                        .authorities(new SimpleGrantedAuthority("ROLE_learning-user"))
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAdminShouldReturnForbiddenWhenRequestHasLearningUserRole() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get("/test/admin")
                        .with(
                                SecurityMockMvcRequestPostProcessors.jwt()
                                        .authorities(new SimpleGrantedAuthority("ROLE_learning-user"))
                        ))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testAdminShouldReturnOkWhenRequestHasLearningAdminRole() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.get("/test/admin")
                        .with(
                                SecurityMockMvcRequestPostProcessors.jwt()
                                        .authorities(new SimpleGrantedAuthority("ROLE_learning-admin"))
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

// this approach is not working with JWT
// jakarta.servlet.ServletException: Request processing failed: java.lang.ClassCastException: class org.springframework.security.authentication.UsernamePasswordAuthenticationToken cannot be cast to class org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken (org.springframework.security.authentication.UsernamePasswordAuthenticationToken and org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken are in unnamed module of loader 'app'
//    @Test
//    @WithMockUser(roles = "learning-admin")
//    void test_WithMockUser_AdminShouldReturnOkWhenRequestHasLearningAdminRole() throws Exception {
//        mvc
//                .perform(MockMvcRequestBuilders.get("/test/admin"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
}
