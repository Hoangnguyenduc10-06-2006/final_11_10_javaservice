package job_portal_systemapi.exception;

public class NotFoundJob extends RuntimeException {
    public NotFoundJob(String message) {
        super(message);
    }
}
