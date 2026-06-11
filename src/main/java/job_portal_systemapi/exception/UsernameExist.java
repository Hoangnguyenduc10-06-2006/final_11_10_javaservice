package job_portal_systemapi.exception;

public class UsernameExist extends RuntimeException {
    public UsernameExist(String message) {
        super(message);
    }
}
