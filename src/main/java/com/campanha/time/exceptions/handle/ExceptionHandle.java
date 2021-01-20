package com.campanha.time.exceptions.handle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.campanha.time.dto.response.BaseResponse;
import com.campanha.time.exceptions.NomeCampanhaIgualException;
import com.campanha.time.exceptions.NomeTimeNaoExisteException;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> defaultErrorHandler(HttpServletRequest req, Exception e) {
        BaseResponse baseResponse = new BaseResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        baseResponse.setStatusCode(httpStatus.value());
        baseResponse.setMessage(e.getMessage());

        return ResponseEntity.status(httpStatus).body( baseResponse );
    }

    @ExceptionHandler(NomeCampanhaIgualException.class)
    public ResponseEntity<BaseResponse> handle(HttpServletRequest req, NomeCampanhaIgualException e) {
        BaseResponse baseResponse = new BaseResponse();
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

        baseResponse.setStatusCode(httpStatus.value());
        baseResponse.setMessage(e.getMessage());

        return ResponseEntity.status(httpStatus).body(baseResponse);
    }

    @ExceptionHandler(NomeTimeNaoExisteException.class)
    public ResponseEntity<BaseResponse> handle(HttpServletRequest req, NomeTimeNaoExisteException e) {
        BaseResponse baseResponse = new BaseResponse();
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

        baseResponse.setStatusCode(httpStatus.value());
        baseResponse.setMessage(e.getMessage());

        return ResponseEntity.status(httpStatus).body(baseResponse);
    }
}
