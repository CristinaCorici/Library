package com.sda.project.imageConvertor;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class ImgConvToByteArr {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        byte[] fileContent = FileUtils.readFileToByteArray(new File("C:\\Users\\iustin.ionescu\\Proiect final\\Library\\src\\main\\resources\\static\\img\\shealy-norman-c-dr-ghidul-vindecarilor-18683.jpg"));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        System.out.println(encodedString);
    }

}
