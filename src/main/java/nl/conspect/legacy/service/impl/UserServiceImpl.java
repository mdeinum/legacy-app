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
import nl.conspect.legacy.service.UserService;
import nl.conspect.legacy.util.RemoteSystemClient;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Marten Deinum
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
        sendEmail(user);
        synchronize(user);
    }

    private void synchronize(final User user) {

        Thread syncThread = new Thread(new Runnable(){
            public void run() {
                String msg = "username:" + user.getUsername() + "|email:" + user.getEmailAddress()+"$";
                try {
                    new RemoteSystemClient().send(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        syncThread.start();
    }

    private void sendEmail(final User user) {
        Thread mailThread = new Thread(new Runnable() {
            public void run() {
                Properties props = new Properties();
                props.setProperty("mail.smtp.host", "localhost");
                props.setProperty("mail.smtp.port", "2525");
                Session session = Session.getInstance(props);

                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress("noreply@ourcompany.io"));
                    msg.setText("Welcome new user: " + user.getDisplayName(), "UTF-8");
                    msg.addRecipients(Message.RecipientType.TO, user.getEmailAddress());
                    msg.setSubject("New User");
                    Transport.send(msg);
                } catch (MessagingException me) {
                    throw new RuntimeException(me);
                }
            }
        });
        mailThread.start();
    }

    public void update(User user) {
        userRepository.save(user);
        synchronize(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findWithUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

}
