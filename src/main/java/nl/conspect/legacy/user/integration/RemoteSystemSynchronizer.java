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

import nl.conspect.legacy.user.AbstractUserEvent;
import nl.conspect.legacy.user.User;
import nl.conspect.legacy.util.RemoteSystemClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class RemoteSystemSynchronizer implements ApplicationListener<AbstractUserEvent> {

    private final RemoteSystemClient client;

    @Autowired
    RemoteSystemSynchronizer(RemoteSystemClient client) {
        this.client = client;
    }


    private void synchronize(final User user) {
        String msg = "username:" + user.getUsername() + "|email:" + user.getEmailAddress() + "$";
        try {
            client.send(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void onApplicationEvent(AbstractUserEvent event) {
        synchronize(event.getUser());
    }
}