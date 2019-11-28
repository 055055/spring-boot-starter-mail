package com.example.sendemail.service;

import com.example.sendemail.dao.MemberRepository;
import com.example.sendemail.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MainService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void saveExcel(){
        // Excel 2007 이상
        try(Workbook xlsxWb = new HSSFWorkbook()) {
            // *** Sheet-------------------------------------------------
            // Sheet 생성
            Sheet sheet1 = xlsxWb.createSheet("firstSheet");

            // *** Style--------------------------------------------------
            // Cell 스타일 생성
            CellStyle cellStyle = xlsxWb.createCellStyle();
            // 줄 바꿈
            cellStyle.setWrapText(true);

            Row row = null;
            Cell cell = null;
            //----------------------------------------------------------
            List<Member> member = memberRepository.findAll();
            for(int i = 0;i<member.size();i++){
                // 컬럼 너비 설정
                sheet1.setColumnWidth(i, 10000);
                row = sheet1.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(member.get(i).getMemberSeq());
                cell = row.createCell(1);
                cell.setCellValue(member.get(i).getMemberBirth());
                cell = row.createCell(2);
                cell.setCellValue(member.get(i).getMemberId());
                cell.setCellStyle(cellStyle); // 셀 스타일 적용
            }
            // excel 파일 저장
            try(OutputStream fos = new FileOutputStream("./testExcel.xls")){
                xlsxWb.write(fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Member> getExcel() {

        Member member = null;
        List list = new ArrayList();

        try(FileInputStream fis = new FileInputStream(new File("./testExcel.xls"));
            Workbook xlsxWb = new HSSFWorkbook(fis)){  // Excel 2007 이상
            if(xlsxWb.getNumberOfSheets()!= 0){
                for(int i =0; i<xlsxWb.getNumberOfSheets();i++) {
                    Sheet sheet =  xlsxWb.getSheetAt(i);
                    //
                    int rows = sheet.getPhysicalNumberOfRows();
                    for(int j=0; j<rows; j++){
                        Row row =  sheet.getRow(j);
                        member = new Member();
                        member.setMemberId(String.valueOf(row.getCell(0)));
                        member.setMemberBirth(String.valueOf(row.getCell(1)));
                        member.setMemberName(String.valueOf(row.getCell(2)));
                        list.add(member);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    //그냥 텍스트 메일
    public void sendMail() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject("hi");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("your email address"));
        message.setText("hi");
        message.setSentDate(new Date());
        javaMailSender.send(message);
    }

    //첨부파일 메일
    public void sendMailAttachment() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("your email address");
        helper.setSubject("hi2");
        helper.setText("attachment");

        FileSystemResource file
                = new FileSystemResource(new File("./testExcel.xls"));
        helper.addAttachment("testExcel.xls", file);
        javaMailSender.send(message);

    }
}
