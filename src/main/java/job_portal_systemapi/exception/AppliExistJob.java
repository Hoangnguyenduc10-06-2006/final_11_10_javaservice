package job_portal_systemapi.exception;

public class AppliExistJob extends RuntimeException {
    public AppliExistJob(String message) {
        super(message);
    }
}
