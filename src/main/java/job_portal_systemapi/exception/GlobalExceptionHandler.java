package job_portal_systemapi.exception;


import job_portal_systemapi.model.dto.response.ApiDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String, String>>>
    handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getFieldErrors()) {
            errors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Lỗi xác thực dữ liệu",
                        null,
                        errors,
                        HttpStatus.BAD_REQUEST
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UsernameExist.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleUsernameExist(UsernameExist ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Username đã tồn tại",
                        null,
                        ex.getMessage(),
                        HttpStatus.CONFLICT
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NotFoundUser.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleNotFoundUser(NotFoundUser ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Không tìm thấy user",
                        null,
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotFoundJob.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleNotFoundUser(NotFoundJob ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Không tìm thấy thông tin tuyển dụng",
                        null,
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND
                ),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(jobStatusException.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleNotFoundUser(jobStatusException ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Tin tuyển dụng chưa được duyệt hoặc đã đóng",
                        null,
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AppliExistJob.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleEmailExist(AppliExistJob ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Bạn đã ứng tuyển công việc này rồi ",
                        null,
                        ex.getMessage(),
                        HttpStatus.CONFLICT
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(EmailExist.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleEmailExist(EmailExist ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Email đã tồn tại",
                        null,
                        ex.getMessage(),
                        HttpStatus.CONFLICT
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ConfirmPassNotTrue.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleConfirmPassNotTrue(ConfirmPassNotTrue ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Mật khẩu xác nhận không khớp",
                        null,
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(JWTValidate.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleJWTValidate(JWTValidate ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Lỗi JWT",
                        null,
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex
    ) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Lỗi kiểu dữ liệu",
                        null,
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex
    ) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Lỗi đọc dữ liệu JSON",
                        null,
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleException(Exception ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Lỗi server",
                        null,
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiDataResponse<String>>
    handleBadCredentialsException(BadCredentialsException ex) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        false,
                        "Sai tài khoản hoặc mật khẩu",
                        null,
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED
                ),
                HttpStatus.UNAUTHORIZED
        );
    }
}