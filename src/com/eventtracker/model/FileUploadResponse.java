package com.eventtracker.model;

import lombok.Data;

@Data
public class FileUploadResponse {
    private final String status;
    private final String message;
    private String filePath;
}
