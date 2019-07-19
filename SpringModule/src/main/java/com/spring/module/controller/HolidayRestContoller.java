package com.spring.module.controller;

import com.spring.module.model.Holiday;
import com.spring.module.service.HolidayService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class HolidayRestContoller {

    @Autowired
    HolidayService holidayService;

    @GetMapping("/holidays")
    public List<Holiday> getListOfHolidays() throws IOException {
        List<Holiday> listOfHolidays=new ArrayList<>();
        List<Holiday> listOfHolidays2=new ArrayList<>();
        listOfHolidays=holidayService.getHolidays();

        for(Holiday value: listOfHolidays){
//            convertFromMultipartFileToByteFormatFile(value.getImage());
            convertByteArrayToBufferedImage(value.getImage());
        }

        return listOfHolidays;
    }

    // convert byte array back to BufferedImage
    void convertByteArrayToBufferedImage(Byte[] byteImageObject) throws IOException {

        byte[] bytes = ArrayUtils.toPrimitive(byteImageObject);

        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage bImageFromConvert = ImageIO.read(in);

        ImageIO.write(bImageFromConvert, "jpg", new File(
                "SpringModule/src/main/resources/1.jpg"));
        System.out.println("Done");
    }

    byte[]  convertToByteOnly(Byte[] byteImageObject) throws IOException {
        byte[] bytes = ArrayUtils.toPrimitive(byteImageObject);
        return bytes;
}

@GetMapping(value = "/image/{holidayId}",produces = MediaType.IMAGE_JPEG_VALUE)

    public byte[] getImageByHolidayId(@PathVariable int holidayId) throws IOException {

        List<Holiday> listOfHolidays=holidayService.getHolidays();

        byte[] bytes = new byte[0];

        for(Holiday value: listOfHolidays){

            if(value.getId()==holidayId){
                bytes = convertToByteOnly(value.getImage());
            }
        }
        return bytes;
    }

}
