package com.fitech.app.users.infrastructure.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private final SendGrid sendGrid;
    private final String fromEmail;
    private final String appUrl;

    public EmailService(
            @Value("${sendgrid.api-key}") String apiKey,
            @Value("${sendgrid.from-email}") String fromEmail,
            @Value("${app.url}") String appUrl) {
        this.sendGrid = new SendGrid(apiKey);
        this.fromEmail = fromEmail;
        this.appUrl = appUrl;
    }

    public void sendVerificationEmail(String toEmail, String token) throws IOException {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        String subject = "Verifica tu cuenta de FiTech";
        
        String verificationLink = String.format("%s/verify-email?token=%s", appUrl, token);
        String htmlContent = String.format("""
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                <h2 style="color: #333;">¡Bienvenido a FiTech!</h2>
                <p>Gracias por registrarte. Para completar tu registro, por favor verifica tu dirección de correo electrónico haciendo clic en el siguiente botón:</p>
                <div style="text-align: center; margin: 30px 0;">
                    <a href="%s" style="background-color: #4CAF50; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; display: inline-block;">
                        Verificar mi email
                    </a>
                </div>
                <p>O copia y pega el siguiente enlace en tu navegador:</p>
                <p style="word-break: break-all;">%s</p>
                <p style="color: #666; font-size: 0.9em;">Este enlace expirará en 24 horas.</p>
                <p>Si no creaste esta cuenta, puedes ignorar este mensaje.</p>
            </div>
            """, verificationLink, verificationLink);

        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        if (response.getStatusCode() >= 400) {
            throw new IOException("Error sending email: " + response.getBody());
        }
    }

    public void sendTestEmail(String toEmail) throws IOException {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        String subject = "Prueba de Email - FiTech";
        
        String htmlContent = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                <h2 style="color: #333;">¡Prueba de Email Exitosa!</h2>
                <p>Este es un email de prueba para verificar la configuración de SendGrid en FiTech.</p>
                <div style="text-align: center; margin: 30px 0;">
                    <p style="color: #4CAF50; font-size: 1.2em;">✅ Si recibes este email, la configuración está correcta.</p>
                </div>
                <p style="color: #666; font-size: 0.9em;">Fecha y hora de envío: %s</p>
            </div>
            """.formatted(java.time.LocalDateTime.now());

        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        if (response.getStatusCode() >= 400) {
            throw new IOException("Error sending email: " + response.getBody());
        }
    }
} 