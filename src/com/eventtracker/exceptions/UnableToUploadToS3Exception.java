package com.eventtracker.exceptions;

public class UnableToUploadToS3Exception extends RuntimeException {

    public UnableToUploadToS3Exception(Exception e) {
        super(e);
    }

    private static final long serialVersionUID = -2648041318033272322L;

}
