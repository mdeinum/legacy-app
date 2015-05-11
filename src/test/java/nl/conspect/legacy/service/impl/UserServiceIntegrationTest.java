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

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import nl.conspect.legacy.RemoteSystemEmulator;
import nl.conspect.legacy.domain.User;
import nl.conspect.legacy.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;

/**
 * Created by marten on 17-04-15.
 */
@ContextConfiguration(locations = {"classpath:/META-INF/spring/applicationContext.xml"})
public class UserServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    private GreenMail greenMail;
    private RemoteSystemEmulator emulator;

    @Autowired
    private UserService userService;

    @Before
    public void setup() throws Exception {
        greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        greenMail.start();

        emulator = new RemoteSystemEmulator();
        emulator.start();
    }

    @After
    public void cleanUp() throws Exception {
        greenMail.stop();
        emulator.stop();
    }


    @Test
    public void testNewUser() throws Exception {
        User user = new User();
        user.setDisplayName("Foo Bar");
        user.setEmailAddress("foo@somewhere.org");
        user.setUsername("foobar");
        user.setPassword("password");

        userService.save(user);

        Thread.sleep(100);

        int count = countRowsInTable("user");
        assertEquals(1, count);

        MimeMessage[] mimeMessages = greenMail.getReceivedMessages();
        assertEquals(1, mimeMessages.length);

        assertEquals("username:foobar|email:foo@somewhere.org$", emulator.getReceived());
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setDisplayName("Foo Bar");
        user.setEmailAddress("foo@somewhere.com");
        user.setUsername("foobar");
        user.setPassword("password");

        userService.update(user);

        Thread.sleep(100);

        int count = countRowsInTable("user");
        assertEquals(1, count);

        MimeMessage[] mimeMessages = greenMail.getReceivedMessages();
        assertEquals(0, mimeMessages.length);

        assertEquals("username:foobar|email:foo@somewhere.com$", emulator.getReceived());
    }

}