package pl.somehost.javaexpert.mailservice;

import pl.somehost.javaexpert.config.ExpertConfigurator;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class SimpleMailSender {

    SimpleMailComposer simpleMailComposer;

    public SimpleMailSender(SimpleMailComposer simpleMailComposer) {
        this.simpleMailComposer = simpleMailComposer;

    }

    Mailer mailer = MailerBuilder
            .withSMTPServer(ExpertConfigurator.MAIL_HOST, ExpertConfigurator.MAIL_PORT, ExpertConfigurator.EMAIL_USER, ExpertConfigurator.EMAIL_PASSOWRD)
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .withSessionTimeout(10 * 1000)
            .clearEmailAddressCriteria() // turns off email validation
            .withDebugLogging(ExpertConfigurator.EMAIL_DEBUGGING)
            .buildMailer();

    public void sendmail() {
        mailer.sendMail(EmailBuilder.startingBlank()
                .from(ExpertConfigurator.EMAIL_FROM_NAME, ExpertConfigurator.EMAIL_USER_FROM)
                .to(ExpertConfigurator.EMAIL_TO_NAME, ExpertConfigurator.EMAIL_USER_TO)
                .withSubject(simpleMailComposer.getEailSubject())
                .withPlainText(simpleMailComposer.getEmailText())
                .buildEmail());
    }
}
