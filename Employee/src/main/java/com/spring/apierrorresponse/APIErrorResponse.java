package com.spring.apierrorresponse;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIErrorResponse {
	
	private LocalDateTime timeStamp;
	private int statusCode;
	private String errorMessage;
}