package com.example.DonnaPizza.MVC.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private String subject;

    private String message;

    private String receiver;

    private MultipartFile attachment;
}
