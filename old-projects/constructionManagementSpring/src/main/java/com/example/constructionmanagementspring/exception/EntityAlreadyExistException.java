package com.example.constructionmanagementspring.exception;

import java.util.NoSuchElementException;

public class EntityAlreadyExistException extends NoSuchElementException {

    public EntityAlreadyExistException(String s) {
        super(s);
    }
}
