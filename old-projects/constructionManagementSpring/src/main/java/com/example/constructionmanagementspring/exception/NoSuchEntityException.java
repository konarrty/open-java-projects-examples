package com.example.constructionmanagementspring.exception;

import java.util.NoSuchElementException;

public class NoSuchEntityException extends NoSuchElementException {

    public NoSuchEntityException(String s) {
        super(s);
    }
}
