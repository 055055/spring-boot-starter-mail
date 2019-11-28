package com.example.sendemail.controller;

import com.example.sendemail.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void saveMember_create_Test() throws Exception {
        //given
        Member member = Member.builder()
                        .memberName("jay")
                        .memberId("055055")
                        .memberBirth("18880227")
                        .build();


        mockMvc.perform(post("/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(jsonPath("memberName", is("jay")))
                .andExpect(jsonPath("memberId",is("055055")))
                .andExpect(jsonPath("memberBirth",is("18880227")))
                .andExpect(jsonPath("memberSeq").exists())
                .andExpect(jsonPath("modDate").exists())
                .andExpect(jsonPath("regDate").exists())
                .andDo(print());
    }
}