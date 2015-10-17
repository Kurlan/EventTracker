package com.eventtracker.utils;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.eventtracker.exceptions.UnableToUploadToS3Exception;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class FileUploadUtils {

    private final String BUCKET = "kingofthehill";
    private final String CDN_URL = "dk01572ibg6u2.cloudfront.net";

    public String uploadFile(MultipartFile file) {
        String keyname = UUID.randomUUID().toString();
        TransferManager tm;

        String stage = System.getProperty("stage");
        log.info("STAGE: " + stage);
        if ("desktop".equals(stage)) {
            tm = new TransferManager(new EnvironmentVariableCredentialsProvider());
        } else {
            tm = new TransferManager(new SystemPropertiesCredentialsProvider());
        }

        try {
            AccessControlList acl = new AccessControlList();
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, keyname, file.getInputStream(),
                    objectMetadata).withAccessControlList(acl);
            Upload upload = tm.upload(putObjectRequest);
            upload.waitForUploadResult();
            log.info("Upload is done: " + upload.isDone());
            return CDN_URL + "/" + keyname;
        } catch (Exception e) {
            log.error(e);
            throw new UnableToUploadToS3Exception(e);
        }
    }

}