package com.logic.nd.exceptions;

import com.logic.api.INDProof;
import com.logic.parser.ParseException;

public class NDSyntaxException extends RuntimeException {

    private final ParseException exception;
    private final INDProof proof;

    public NDSyntaxException(INDProof proof, ParseException exception) {
        this.proof = proof;
        this.exception = exception;
    }

    public INDProof getProof() {
        return proof;
    }

    public ParseException getException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }
}
