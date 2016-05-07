package diplom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created on 07.05.2016.
 */
@Service
public class MailService {
    @Autowired
    private MailSender mailSender;
    private String to;
    private String from;
    private String subject;
    private String text;

    private void sendMail(){
        SimpleMailMessage smm = new SimpleMailMessage();
        if (subject!=null)
            smm.setSubject(subject);
        if (text!=null)
            smm.setText(text);
        else
            smm.setText("");
        if (from!=null)
            smm.setFrom(from);
        else
            smm.setFrom("vsuammsystem@gmail.com");
        if (to!=null) {
            smm.setTo(to);
            mailSender.send(smm);
        }
    }

    public Builder builder(){
        return new Builder();
    }

    public class Builder{
        public Builder to(String _to){
            MailService.this.to = _to;
            return this;
        }

        public Builder subject(String _subject){
            MailService.this.subject = _subject;
            return this;
        }

        public Builder text(String _text){
            MailService.this.text = _text;
            return this;
        }

        public Builder from(String _from){
            MailService.this.from = _from;
            return this;
        }

        public void send(){
            MailService.this.sendMail();
        }
    }
}
