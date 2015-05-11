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

package nl.conspect.legacy.user.web;

import nl.conspect.legacy.user.User;
import nl.conspect.legacy.user.UserService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * @author marten
 */
public class LoginController extends AbstractController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = ServletRequestUtils.getStringParameter(request, "j_username");
        String password = ServletRequestUtils.getStringParameter(request, "j_password");

        User user = userService.login(username, password);
        if (user != null) {
            WebUtils.setSessionAttribute(request, "currentUser", user);
            return new ModelAndView("account");
        }
        return new ModelAndView("index", Collections.singletonMap("msg", "Wrong username/password combination."));
    }
}
