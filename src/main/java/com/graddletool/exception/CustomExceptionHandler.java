package com.graddletool.exception;

import javax.inject.Singleton;

import com.graddletool.playload.ApiResponse;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

@Produces
@Singleton
@Requires(classes = { CustomException.class, ExceptionHandler.class })

public class CustomExceptionHandler implements ExceptionHandler<CustomException, HttpResponse<ApiResponse>> {

	@Override
	public HttpResponse<ApiResponse> handle(HttpRequest request, CustomException exception) {

		String msg = exception.getMessage();

		return HttpResponse.ok(new ApiResponse(msg, false));

	}

}
