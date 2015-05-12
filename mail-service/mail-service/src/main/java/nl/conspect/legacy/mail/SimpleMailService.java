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

package nl.conspect.legacy.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service("mailService")
class SimpleMailService implements MailService {

    private final Logger logger = LoggerFactory.getLogger(SimpleMailService.class);
    private final JavaMailSender mailSender;

    @Autowired
    SimpleMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendEmail(final SendEmail sendEmail) {
        logger.info("Sending mail: {}", sendEmail);
        this.mailSender.send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage msg) throws Exception {
                msg.setFrom(new InternetAddress("noreply@ourcompany.io"));
                msg.setText(sendEmail.getBody());
                msg.addRecipients(Message.RecipientType.TO, sendEmail.getRecipient());
                msg.setSubject(sendEmail.getSubject());
            }
        });
    }
}