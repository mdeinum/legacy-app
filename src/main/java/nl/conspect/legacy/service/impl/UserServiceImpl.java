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

package nl.conspect.legacy.service.impl;

import nl.conspect.legacy.domain.User;
import nl.conspect.legacy.repository.UserRepository;
import nl.conspect.legacy.service.MailService;
import nl.conspect.legacy.service.UserService;

/**
 * @author Marten Deinum
 */
public class UserServiceImpl implements UserService {

    private final MailService mailService;
    private final RemoteSystemSynchronizer synchronizer;
    private final UserRepository userRepository;

    public UserServiceImpl(MailService mailService, RemoteSystemSynchronizer synchronizer, UserRepository userRepository) {
        this.mailService = mailService;
        this.synchronizer = synchronizer;
        this.userRepository = userRepository;
    }

    public void save(User user) {
        this.userRepository.save(user);
        this.sendEmail(user);
        this.synchronizer.synchronize(user);
    }

    private void sendEmail(User user) {
        String body = "Welcome new user: " + user.getDisplayName();
        this.mailService.sendEmail(new SendEmail("New User", user.getEmailAddress(), body));
    }

    public void update(User user) {
        this.userRepository.save(user);
        this.synchronizer.synchronize(user);
    }

    public User login(String username, String password) {
        User user = this.userRepository.findWithUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
