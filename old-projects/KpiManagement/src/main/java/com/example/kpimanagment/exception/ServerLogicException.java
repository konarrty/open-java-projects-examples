package com.example.kpimanagment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerLogicException extends RuntimeException {

    public ServerLogicException(String s ) {
        super(s);
    }

}
