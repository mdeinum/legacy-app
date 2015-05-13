/*
 * Copyright 2000-2015 the original author or authors.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.conspect.legacy.web;

import nl.conspect.legacy.domain.User;
import nl.conspect.legacy.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author marten
 */
@ContextConfiguration()
@WebAppConfiguration
public class UserControllerIntegrationTest extends AbstractJUnit4SpringContextTests{

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
    }

    @Test
    public void userRegistration() throws Exception {
        mvc.perform(
                post("/newuser")
                        .param("id", "123")
                        .param("username", "foobar")
                        .param("password", "password")
                        .param("displayName", "Foo Bar")
                        .param("emailAddress", "foo@somewhere.org")
                        .param("emailValidation", "foo@somewhere.org")
                        .param("passwordValidation", "password")
        ).andExpect(MockMvcResultMatchers.model().hasNoErrors());

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userService, times(1)).save(argumentCaptor.capture());

        User user = argumentCaptor.getValue();
        assertThat(user.getUsername(), is("foobar"));
        assertThat(user.getPassword(), is("password"));
        assertThat(user.getDisplayName(), is("Foo Bar"));
        assertThat(user.getEmailAddress(), is("foo@somewhere.org"));
        assertThat(user.getId(), is(0L)); // Binding id is disallowed hence it should remain 0
    }

    @Configuration
    @ImportResource("classpath:/META-INF/spring/dispatcher-servlet.xml")
    public static class TestConfiguration {

        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }



}