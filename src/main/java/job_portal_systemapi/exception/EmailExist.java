package job_portal_systemapi.exception;

public class EmailExist extends RuntimeException {
    public EmailExist(String message) {
        super(message);
    }
}
