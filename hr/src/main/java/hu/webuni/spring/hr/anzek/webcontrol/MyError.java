/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.webcontrol;


import java.util.List;
import org.springframework.validation.FieldError;


/**
 *
 * @author User
 */
class MyError {
    
    private String message;
    private int errorCode;

    private List<FieldError> fieldErrors;

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
    
    public MyError(String message,
                   int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
