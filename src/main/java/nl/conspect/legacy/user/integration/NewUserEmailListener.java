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

package nl.conspect.legacy.user.integration;

import nl.conspect.legacy.mail.MailService;
import nl.conspect.legacy.mail.SendEmail;
import nl.conspect.legacy.user.NewUserCreated;
import nl.conspect.legacy.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author marten
 */
@Component
class NewUserEmailListener implements ApplicationListener<NewUserCreated> {

    private final MailService mailService;

    @Autowired
    public NewUserEmailListener(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(NewUserCreated event) {
        final User user = event.getUser();
        String body = "Welcome new user: " + user.getDisplayName();
        this.mailService.sendEmail(new SendEmail("New User", user.getEmailAddress(), body));

    }
}
