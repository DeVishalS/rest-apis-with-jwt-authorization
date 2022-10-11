package com.example.hellosecurity.dto.response;

import com.example.hellosecurity.dto.error.StandardErrorResponse;
import lombok.Builder;

@Builder
public record StandardResponse<T> (T data, boolean isSuccess, StandardErrorResponse error){

    public static <T> StandardResponse buildSuccessResponse(T data){
        return builder().isSuccess(true).data(data).build();
    }

    public static StandardResponse buildFailureResponse(StandardErrorResponse error){
        return builder().isSuccess(false).error(error).build();
    }
}
