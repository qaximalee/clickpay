package com.clickpay.controller.file;

import com.clickpay.utils.ControllerConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(ControllerConstants.CNIC_FILE)
public class CNICController {

    @PostMapping("/upload")
    public ResponseEntity uploadCNIC(@RequestParam("files") MultipartFile[] files, @RequestParam("id") Long id) {
        System.out.println("User id: "+id);
        System.out.println(files[0].getName());

        for (MultipartFile file : files) {
            File savingFile = new File("D://ss//"+file.getName()+".png");
            try {
                file.transferTo(savingFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("CNIC pictures uploaded.");
    }
}
