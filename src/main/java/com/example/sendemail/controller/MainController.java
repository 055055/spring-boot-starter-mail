package com.example.sendemail.controller;

import com.example.sendemail.model.Member;
import com.example.sendemail.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.mail.MessagingException;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MainService mainService;

    @PostMapping("/save/excel")
    public void saveExcel(){
        mainService.saveExcel();
    }

    @GetMapping("/excel")
    public @ResponseBody
    List<Member> getExcel(){
        return mainService.getExcel();
    }

    @PostMapping("/mail")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void mail() throws MessagingException {
        mainService.sendMail();
    }

    @PostMapping("mailAttachment")
    public void mailAttachment() throws MessagingException {
        mainService.sendMailAttachment();
    }
}
