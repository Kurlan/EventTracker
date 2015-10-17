package com.eventtracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eventtracker.exceptions.UnableToUploadToS3Exception;
import com.eventtracker.model.FileUploadResponse;
import com.eventtracker.model.Party;
import com.eventtracker.utils.FileUploadUtils;
import com.eventtracker.utils.PartyUtils;

@Controller
@RequestMapping("/party")
public class PartyController {

    private final PartyUtils partyUtils;
    private final FileUploadUtils fileUploadUtils;

    @Autowired
    public PartyController(PartyUtils partyUtils, FileUploadUtils fileUploadUtils) {
        this.partyUtils = partyUtils;
        this.fileUploadUtils = fileUploadUtils;
    }

    @RequestMapping(value = "/{partyId}", method = RequestMethod.GET)
    @ResponseBody
    public Party getParty(@PathVariable String partyId) {
        return partyUtils.getParty(partyId);
    }

    @RequestMapping(value = "/byUser/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Party> getPartyByUser(@PathVariable String userId) {
        return partyUtils.getPartyByUser(userId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Party createParty(@RequestParam String name, @RequestParam String description,
            @RequestParam("userId") String userId, @RequestParam("relationshipType") String relationshipType,
            @RequestParam(value = "photoURL", required = false) String photoURL) {

        return partyUtils.createParty(name, description, userId, relationshipType, photoURL);
    }

    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public FileUploadResponse handleFileUpload(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new FileUploadResponse("ERROR", "FILE EMPTY");
        }

        try {
            String filePath = fileUploadUtils.uploadFile(file);
            FileUploadResponse fileUploadResponse = new FileUploadResponse("SUCCESS", "SUCESS");
            fileUploadResponse.setFilePath(filePath);
            return fileUploadResponse;
        } catch (UnableToUploadToS3Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new FileUploadResponse("ERROR", "UNABLE TO UPLOAD TO S3");
        }

    }
}
