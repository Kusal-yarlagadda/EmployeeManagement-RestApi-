//package com.spring.globalexceptionhandler;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import com.spring.apierrorresponse.APIErrorResponse;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors()
//					.forEach((error) -> {
//						 String fieldName = ((FieldError) error).getField();
//				            String message = error.getDefaultMessage();
//				            errors.put(fieldName, message);
//					});
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//							 .header("info", "Error Handling")
//							 .body(errors);
//	}
//	
//	@ExceptionHandler(IllegalArgumentException.class)
//	public ResponseEntity<APIErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception){
//		APIErrorResponse illegalErrorResponse = new APIErrorResponse();
//		illegalErrorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
//		illegalErrorResponse.setErrorMessage(exception.getMessage());
//		illegalErrorResponse.setTimeStamp(LocalDateTime.now());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//							 .header("info", "Error Information")
//							 .body(illegalErrorResponse);
//	}
//}
