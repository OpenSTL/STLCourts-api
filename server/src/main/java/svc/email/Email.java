package svc.email;

/**
 * Encapsulates elements of an email
 */
public class Email {

    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String body;
    private String subject;
    private String language;
    private String content;

    public Email(String from, String to, String cc, String bcc, String body, String subject, String language,
            String content) {
        super();
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.body = body;
        this.subject = subject;
        this.language = language;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
