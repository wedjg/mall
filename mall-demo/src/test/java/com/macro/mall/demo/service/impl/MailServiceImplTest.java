package com.macro.mall.demo.service.impl;

import com.macro.mall.demo.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    private static final String TO = "125490772@qq.com";
    private static final String SUBJECT = "测试邮件";
    private static final String CONTENT = "来自mall的测试邮件";

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail(TO, SUBJECT, CONTENT);
    }

    @Test
    public void sendHtmlMail() {
    }

    @Test
    public void sendAttachmentsMail() {
    }

    @Test
    public void sendInlineResourceMail() {
    }
}