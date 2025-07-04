package com.logic.nd.exceptions;

import com.logic.api.INDProof;
import com.logic.parser.TokenMgrError;

public class NDLexicalException extends RuntimeException {

    private final TokenMgrError exception;
    private final INDProof proof;

    public NDLexicalException(INDProof proof, TokenMgrError exception) {
        this.proof = proof;
        this.exception = exception;
    }

    public TokenMgrError getException() {
        return exception;
    }

    public INDProof getProof() {
        return proof;
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }
}
