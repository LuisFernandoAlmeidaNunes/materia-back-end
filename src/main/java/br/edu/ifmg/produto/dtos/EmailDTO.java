package br.edu.ifmg.produto.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

    @NotBlank
    @Email
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;

    public @NotBlank @Email String getTo() {
        return to;
    }

    public void setTo(@NotBlank @Email String to) {
        this.to = to;
    }

    public @NotBlank String getSubject() {
        return subject;
    }

    public void setSubject(@NotBlank String subject) {
        this.subject = subject;
    }

    public @NotBlank String getBody() {
        return body;
    }

    public void setBody(@NotBlank String body) {
        this.body = body;
    }

    public EmailDTO() {
    }

    public EmailDTO(String body, String subject, String to) {
        this.body = body;
        this.subject = subject;
        this.to = to;
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
