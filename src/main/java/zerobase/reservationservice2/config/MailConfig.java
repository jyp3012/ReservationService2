package zerobase.reservationservice2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailConfig {

//Gmail 사용 SMTP 라는 프로토콜 사용

    private final JavaMailSender javaMailSender;

    public boolean sendMail(String mail, String subject, String text) {
        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper =
                        new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);

            }
        };

        try {
            javaMailSender.send(msg);
            result =true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;

    }

}
