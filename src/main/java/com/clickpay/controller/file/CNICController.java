package com.clickpay.controller.file;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.utils.ControllerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(ControllerConstants.CNIC_FILE)
public class CNICController {

    @Value("${full.domain.name}")
    private String SERVER_HOST_NAME;

    private static final String S_JPG = "jpg";
    private static final String C_JPG = "JPG";
    private static final String S_JPEG = "jpeg";
    private static final String C_JPEG = "JPEG";
    private static final String S_PNG = "png";
    private static final String C_PNG = "PNG";

    @PostMapping("/upload")
    public ResponseEntity uploadCNIC(@RequestParam("files") MultipartFile[] files, @RequestParam("id") Long id) throws BadRequestException {
        System.out.println("User id: "+id);
        System.out.println(files[0].getName());

        List<String> urlsOfCNICImages = new ArrayList<>();

        for (MultipartFile file : files) {
            // check attached file is an image
            if(!MediaType.valueOf(file.getContentType()).getType().equalsIgnoreCase("image"))
                throw new BadRequestException("Provided file is not an image.");
            // check if it contains any special characters except ' " "
            if (Pattern.compile("[^0-9A-Za-z' ._-]").matcher(file.getOriginalFilename()).find()) {
                System.out.println("Please remove special character from the file name (i.e Simple Name.pdf can have . '-_ special characters).");
                throw new BadRequestException("Please remove special character from the file name (i.e Simple Name.pdf can have . '-_ special characters).");
            }
            long timestamp = System.currentTimeMillis();

            File savingFile = new File("D://ss//"+timestamp + file.getOriginalFilename());
            try {
                file.transferTo(savingFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            urlsOfCNICImages.add(SERVER_HOST_NAME + "/api/v1/image/download?filename=" + timestamp + file.getOriginalFilename());

        }
        return ResponseEntity.status(HttpStatus.OK).body(urlsOfCNICImages);
    }

    @GetMapping("/download")
    public ResponseEntity download(@RequestParam("filename") String filename)
            throws BadRequestException {
        String[] filenameSplit = filename.split("[.]");
        String contentType = filenameSplit[1];
        MediaType mediaType = null;
        switch (contentType) {
            case S_JPG:
            case S_JPEG:
            case C_JPG:
            case C_JPEG:
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case S_PNG:
            case C_PNG:
                mediaType = MediaType.IMAGE_PNG;
                break;
            default:
                throw new BadRequestException("Image type is not supported");
        }

        byte[] data = null;
        File file = new File("D://ss//" + filename);

        try {
            data = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
            System.out.println("Error getting image file.");
        }

        if(data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(data.length)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename)
                    .body(data);
    }

}
