package com.etz.cashpoint.exception;

import com.etz.cashpoint.dto.response.BaseResponse;
import org.hibernate.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mishael.harry
 */
@RestController
@ControllerAdvice
public class AppExceptionHandler {
    
    /*@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> generalException(Exception ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", null);
         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(CashpointException.class)
    public ResponseEntity<BaseResponse> cashpointException(CashpointException ex) {

        return new ResponseEntity<>(new BaseResponse<>(false, ex.getMessage(), null),
                StringUtils.isEmpty(ex.getStatus())? HttpStatus.BAD_GATEWAY: ex.getStatus());
    }
 
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> notFoundException(Exception ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());        
        ErrorResponse error = new ErrorResponse("Record Not Found", details);
         return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public final ResponseEntity<Object> notAuthorizedException(Exception ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());        
        ErrorResponse error = new ErrorResponse("Not Authorized", details);
         return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<Object> forbiddenException(Exception ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());        
        ErrorResponse error = new ErrorResponse("Access Forbidden", details);
         return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> badRequestException(Exception ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());        
        ErrorResponse error = new ErrorResponse("Bad Request", details);
         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ConstraintViolationException.class, DataException.class,
        JDBCConnectionException.class, LockAcquisitionException.class, PessimisticLockException.class, 
        QueryTimeoutException.class, SQLGrammarException.class, GenericJDBCException.class })
    public final ResponseEntity<Object> sqlException(SQLException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());        
        ErrorResponse error = new ErrorResponse("Server Error", null);
         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
