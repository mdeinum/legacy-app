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
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @author Marten Deinum
 */
public class UserController extends SimpleFormController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    protected void doSubmitAction(Object command) throws Exception {
        NewUserRegistrationForm form = (NewUserRegistrationForm) command;

        User user = new User();
        user.setPassword(form.getPassword());
        user.setDisplayName(form.getDisplayName());
        user.setEmailAddress(form.getEmailAddress());
        user.setUsername(form.getUsername());
        userService.save(user);
    }

}
