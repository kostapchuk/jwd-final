package com.epam.jwd_final.web.property;

public class SmtpProperty {

    static final String SMTP_AUTH_PROPERTY = "mail.smtp.auth";
    static final String SMTP_STARTTLS_PROPERTY = "mail.smtp.starttls.enable";
    static final String SMTP_HOST_PROPERTY = "mail.smtp.host";
    static final String SMTP_PORT_PROPERTY = "mail.smtp.port";
    static final String SMTP_FROM_PROPERTY = "mail.smtp.from";
    static final String SMTP_USERNAME_PROPERTY = "mail.smtp.username";
    static final String SMTP_PASSWORD_PROPERTY = "mail.smtp.password";

    private final String smtpAuth;
    private final String smtpStarttls;
    private final String smtpHost;
    private final String smtpPort;
    private final String smtpFrom;
    private final String smtpUsername;
    private final String smtpPassword;

    public SmtpProperty(String smtpAuth, String smtpStarttls, String smtpHost, String smtpPort,
                        String smtpFrom, String smtpUsername, String smtpPassword) {
        this.smtpAuth = smtpAuth;
        this.smtpStarttls = smtpStarttls;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.smtpFrom = smtpFrom;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public String getSmtpStarttls() {
        return smtpStarttls;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public String getSmtpFrom() {
        return smtpFrom;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }
}
