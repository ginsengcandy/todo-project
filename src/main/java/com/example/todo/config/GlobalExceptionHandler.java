package com.example.todo.config;

import com.example.todo.customErrors.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            /*
            FieldError 내 정보 - field : 에러 필드명, defaultMessage : 메시지, rejectedValue : 실제 입력값
            FieldError는 ObjectError 상속
            */
            String fieldName = ((FieldError) error).getField(); //필드명 추출(getField()는 FieldError에만 존재하므로 캐스팅 필요)
            String errorMessage = error.getDefaultMessage(); //에러 메시지 추출(ObjectError타입에 이미 존재하므로 캐스팅 불요)
            errors.put(fieldName, errorMessage); //errors Map이 필드명을 key, 에러메시지를 value로 저장
        });

        response.put("errors", errors); //최종 응답 객체에 error 정보 추가
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response); //응답 본문 (Map을 JSON으로 자동 변환 -> 직렬화)
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Map<String, Object>> handleCustomExceptions (
            ServerException ex
    ){
        Map<String, Object> response = new HashMap<>();

        response.put("message", ex.getMessage()); // 에러 메시지
        response.put("errorCode", ex.getErrorCode()); // 에러 코드
        response.put("timestamp", LocalDateTime.now()); // 발생 시각 (선택)

        // 예외에 설정된 HTTP 상태 코드로 응답
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }
}
