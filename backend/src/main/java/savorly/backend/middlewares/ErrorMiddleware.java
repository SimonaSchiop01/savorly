package savorly.backend.middlewares;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import savorly.backend.exceptions.ConflictException;
import savorly.backend.exceptions.NotFoundException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorMiddleware {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error("Not found error", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        404
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<String> handleConflictException(ConflictException e) {
        log.error("Conflict error", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        409
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleAllExceptions(Exception e) {
        log.error("Internal server error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
//    500
}
