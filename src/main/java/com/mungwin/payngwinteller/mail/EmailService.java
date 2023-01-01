package com.mungwin.payngwinteller.mail;

import com.mungwin.payngwinteller.domain.model.mail.EmailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSender mailSender;
    private MimeMessageHelper messageHelper;
    private MimeMessage mimeMessage;
    private final TemplateEngine templateEngine;
    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    public void send(String template, Context ctx, String subject, EmailAddress from, EmailAddress to) {
        send(template, ctx, subject, from, to, null, null);
    }
    public void sendAndCC(String template, Context ctx, String subject, EmailAddress from, EmailAddress to, List<EmailAddress> cc) {
        send(template, ctx, subject, from, to, cc, null);
    }
    public void sendAndBCC(String template, Context ctx, String subject, EmailAddress from, EmailAddress to, List<EmailAddress> bcc) {
        send(template, ctx, subject, from, to, null, bcc);
    }
    public void send(String template, Context ctx, String subject, EmailAddress from, EmailAddress to, List<EmailAddress> cc, List<EmailAddress> bcc) {
        (new Thread(() -> sendStub(template, ctx, subject, from, to, cc, bcc))).start();
    }
    public void sendStub(String template, Context ctx, String subject, EmailAddress from,
                         EmailAddress to, List<EmailAddress> cc, List<EmailAddress> bcc) {
        synchronized (mailSender) {
            try {
                this.mimeMessage = this.mailSender.createMimeMessage();
                this.messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
                logger.info("Sending email to: {}", to);
                ctx.setVariable("appName", appName);
                String content = templateEngine.process(template, ctx);
                from.setPersonal(String.format("%s from %s", from.getPersonal(), appName));
                messageHelper.setFrom(internetAddressFromEmail(from));
                messageHelper.setTo(internetAddressFromEmail(to));
                if(cc != null && !cc.isEmpty()) {
                    try {
                        messageHelper.setCc(cc.stream().map(c -> {
                            try {
                                return internetAddressFromEmail(c);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }).toArray(InternetAddress[]::new));
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                    }
                }
                if(bcc != null && !bcc.isEmpty()) {
                    try {
                        messageHelper.setBcc(bcc.stream().map(c -> {
                            try {
                                return internetAddressFromEmail(c);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }).toArray(InternetAddress[]::new));
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                    }
                }
                messageHelper.setSubject(String.format("[%s] %s",appName, subject));
                messageHelper.setText(content, true);
                mailSender.send(mimeMessage);
                logger.info("Email sent");
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }
    public static InternetAddress internetAddressFromEmail(EmailAddress email) throws Exception {
        return email.getPersonal() == null ?
                new InternetAddress(email.getAddress()) :
                new InternetAddress(email.getAddress(), email.getPersonal());
    }
}
