package com.unsis.scunsis_backend.exception;

import com.unsis.scunsis_backend.constants.Constant;
import org.springframework.http.HttpStatus;
public class ReceiverNotFoundException extends AppException {

    public ReceiverNotFoundException(Long receiverId) {
        super(
                String.format(Constant.RECEIVER_NOT_FOUND, receiverId),
                HttpStatus.NOT_FOUND,
                "RECEIVER_NOT_FOUND"
        );
    }
}