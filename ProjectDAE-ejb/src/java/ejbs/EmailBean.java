package ejbs;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailBean {

    public static final boolean SEND_EMAIL = true;

    @Resource(name = "mail/dae")
    private Session mailSession;

    public void send(String to, String subject, String text) throws MessagingException {

        Message message = new MimeMessage(mailSession);

        try {
            // Adjust the recipients. Here we have only one recipient.
            // The recipient's address must be an object of the InternetAddress class.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Set the message's subject
            message.setSubject(subject);

            // Insert the message's body
            message.setText(text);

            // Adjust the date of sending the message
            Date timeStamp = new Date();
            message.setSentDate(timeStamp);

            // Use the 'send' static method of the Transport class to send the message
            if (SEND_EMAIL) {
                //COMMENTED SO THAT IT DOESN'T SEND EMAILS
                Transport.send(message);
            }

        } catch (MessagingException e) {
            throw e;
        }
    }
}
