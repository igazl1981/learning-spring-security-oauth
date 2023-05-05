package com.igazl.learning.spring.security.oauthlearning.rest;

import com.igazl.learning.spring.security.oauthlearning.security.WebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TestController.class, properties = {"server.ssl.enabled=false"})
@Import(WebSecurityConfig.class)
class TestControllerTest {

    @Autowired
    MockMvc api;

    @Test
    void givenRequestIsAnonymous_whenGetAnonymous_thenUnauthorized() throws Exception {
        api.perform(get("/test/user").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenRequestIsAnonymous_whenGetUser_thenAuthorized() throws Exception {
        api.perform(get("/test/user").with(jwt().authorities(List.of(new SimpleGrantedAuthority("ROLE_learning-user")))
                        .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "ch4mpy"))))
                .andExpect(status().isOk());
        api.perform(get("/test/admin").with(jwt().authorities(List.of(new SimpleGrantedAuthority("ROLE_learning-user")))
                        .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "ch4mpy"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenRequestIsAnonymous_whenGetAdmin_thenUnAuthorized() throws Exception {
        api.perform(
                        get("/test/admin")
                                .with(
                                        jwt()
                                                .authorities(new SimpleGrantedAuthority("SCOPE_learning-user"))
                                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "ch4mpy")))
                )
                .andExpect(status().isForbidden());
    }

//    @Test
//    void givenRequestIsAnonymous_whenGetAdmin_thenAuthorized() throws Exception {
//        api.perform(get("/test/user").with(jwt().authorities(List.of(new SimpleGrantedAuthority("ROLE_learning-admin")))
//                        .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "ch4mpy"))))
//                .andExpect(status().isOk());
//        api.perform(get("/test/admin").with(jwt().authorities(List.of(new SimpleGrantedAuthority("ROLE_learning-admin")))
//                        .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "ch4mpy"))))
//                .andExpect(status().isUnauthorized());
//    }

}