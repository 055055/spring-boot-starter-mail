package com.example.sendemail.controller;

import com.example.sendemail.model.Member;
import com.example.sendemail.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private MainService mainService;

    @PostMapping("/save")
    public Member memberSave(@RequestBody Member member){
        return mainService.saveMember(member);
    }
}
