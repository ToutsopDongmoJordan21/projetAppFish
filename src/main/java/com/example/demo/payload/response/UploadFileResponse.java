package com.example.demo.payload.response;

public class UploadFileResponse {

    public String getFileName() {
        return fileName;
    }

    public UploadFileResponse setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public UploadFileResponse setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getFileUri() {
        return fileUri;
    }

    public UploadFileResponse setFileUri(String fileUri) {
        this.fileUri = fileUri;
        return this;
    }

    public UploadFileResponse(String fileName, String fileType, String fileUri) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUri = fileUri;
    }

    String fileName;
    String fileType;
    String fileUri;

}

