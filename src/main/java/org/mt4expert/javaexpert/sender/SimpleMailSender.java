package org.mt4expert.javaexpert.sender;

import org.mt4expert.javaexpert.config.ExpertConfigurator;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class SimpleMailSender {

    String emailSubject;
    String emailText;

    public SimpleMailSender(String emailSubject, String emailText) {
        this.emailSubject = emailSubject;
        this.emailText = emailText;
    }

    Mailer mailer = MailerBuilder
            .withSMTPServer(ExpertConfigurator.MAIL_HOST, ExpertConfigurator.MAIL_PORT, ExpertConfigurator.EMAIL_USER, ExpertConfigurator.EMAIL_PASSOWRD)
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .withSessionTimeout(10 * 1000)
            .clearEmailAddressCriteria() // turns off email validation
            /*.withDebugLogging(true)*/
            .buildMailer();

    public void sendmail() {
        mailer.sendMail(EmailBuilder.startingBlank()
                .from(ExpertConfigurator.EMAIL_FROM_NAME, ExpertConfigurator.EMAIL_USER_FROM)
                .to(ExpertConfigurator.EMAIL_TO_NAME, ExpertConfigurator.EMAIL_USER_TO)
                .withSubject(emailSubject)
                .withPlainText(emailText)
                .buildEmail());
    }
}
