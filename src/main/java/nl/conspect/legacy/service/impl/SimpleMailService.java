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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class SimpleMailService implements nl.conspect.legacy.service.MailService {

    @Override
    public void sendEmail(final SendEmail sendEmail) {
        Thread mailThread = new Thread(new Runnable() {
            public void run() {
                Properties props = new Properties();
                props.setProperty("mail.smtp.host", "localhost");
                props.setProperty("mail.smtp.port", "2525");
                Session session = Session.getInstance(props);

                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress("noreply@ourcompany.io"));
                    msg.setText(sendEmail.getBody(), "UTF-8");
                    msg.addRecipients(Message.RecipientType.TO, sendEmail.getRecipient());
                    msg.setSubject(sendEmail.getSubject());
                    Transport.send(msg);
                } catch (MessagingException me) {
                    throw new RuntimeException(me);
                }
            }
        });
        mailThread.start();
    }
}