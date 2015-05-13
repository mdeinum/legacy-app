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
import nl.conspect.legacy.domain.UserValidator;
import nl.conspect.legacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Marten Deinum
 */
@Controller
@RequestMapping("/newuser")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public NewUserRegistrationForm formBackingObject() {
        return new NewUserRegistrationForm();
    }

    @RequestMapping(method= RequestMethod.GET)
    public String index() {
        return "newuser";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String newuser(@ModelAttribute("user") NewUserRegistrationForm form, BindingResult errors) {
        if (errors.hasErrors()) {
            return "newuser";
        } else {
            User user = new User();
            user.setPassword(form.getPassword());
            user.setDisplayName(form.getDisplayName());
            user.setEmailAddress(form.getEmailAddress());
            user.setUsername(form.getUsername());
            userService.save(user);
            return "newuser-success";
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
        webDataBinder.setValidator(new UserValidator());
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
