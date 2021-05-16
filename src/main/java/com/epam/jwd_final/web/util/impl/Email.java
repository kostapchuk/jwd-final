package com.epam.jwd_final.web.util.impl;

import com.epam.jwd_final.web.exception.EmailException;
import com.epam.jwd_final.web.property.PropertyLoader;
import com.epam.jwd_final.web.property.SmtpProperty;
import com.epam.jwd_final.web.util.Mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum Email implements Mail {

    INSTANCE;

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";

    private static final String SUBJECT = "TigerBet";
    private static final String TEXT = "You've just successfully joined our community. Welcome!";

    private final SmtpProperty smtpProperty = PropertyLoader.getInstance().loadSmtpProperties();

    public void send(String to) throws EmailException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            Properties props = createProperties();

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    smtpProperty.getSmtpUsername(), smtpProperty.getSmtpPassword()
                            );
                        }
                    });

            try {
                Message message = prepareMessage(session, to);
                Transport.send(message);
            } catch (MessagingException e) {
                throw new EmailException(e.getMessage(), e.getCause());
            }
        };
        executor.execute(task);
        executor.shutdown();
    }

    private Message prepareMessage(Session session, String to) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(smtpProperty.getSmtpFrom()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        return message;
    }

    private Properties createProperties() {
        Properties props = new Properties();
        props.put(MAIL_SMTP_AUTH, smtpProperty.getSmtpAuth());
        props.put(MAIL_SMTP_STARTTLS_ENABLE, smtpProperty.getSmtpStarttls());
        props.put(MAIL_SMTP_HOST, smtpProperty.getSmtpHost());
        props.put(MAIL_SMTP_PORT, smtpProperty.getSmtpPort());
        return props;
    }
}
