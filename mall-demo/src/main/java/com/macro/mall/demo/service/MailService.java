package com.macro.mall.demo.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface MailService{

     void sendSimpleMail(String to, String subject, String content);

     void sendHtmlMail(String to, String subject, String content);

     void sendAttachmentsMail(String to, String subject, String content, String filePath);

     void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}