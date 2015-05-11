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
import nl.conspect.legacy.util.RemoteSystemClient;

import java.io.IOException;

public class RemoteSystemSynchronizer {
    public RemoteSystemSynchronizer() {
    }

    void synchronize(final User user) {

        Thread syncThread = new Thread(new Runnable() {
            public void run() {
                String msg = "username:" + user.getUsername() + "|email:" + user.getEmailAddress() + "$";
                try {
                    new RemoteSystemClient().send(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        syncThread.start();
    }
}