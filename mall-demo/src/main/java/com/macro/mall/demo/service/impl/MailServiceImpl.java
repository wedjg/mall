package com.macro.mall.demo.service.impl;

import com.macro.mall.demo.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 *	@author Liao Jianjian
 *	@since 2019/05/16
 */
@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.fromMail.addr}")
    private String from;

	/**
	 *	发送简单邮件
	 */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }

    }
	

	/**
	 * 发送html邮件
	 * @param to	收件人
	 * @param subject	邮件主题
	 * @param content	邮件正文（html）
	 */
	@Override
	public void sendHtmlMail(String to, String subject, String content) {
		sendSeniorMessage(String to, String subject, String content, null, null, null);
	}

	/**
	 * 发送带附件的邮件
	 * @param to	收件人
	 * @param subject	邮件主题
	 * @param content	邮件正文
	 * @param filePath	附件路径
	 */
	@Override
	public void sendAttachmentsMail(String to, String subject, String content, String filePath){
		sendSeniorMessage(String to, String subject, String content, filePath, null, null);
	}

	/**
	 * 发送带有静态资源的邮件
	 * @param to	收件人
	 * @param subject	邮件主题
	 * @param content	邮件正文
	 * @param rscPath	静态资源（图片）路径
	 * @param rscId
	 */
	@Override
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
		sendSeniorMessage(String to, String subject, String content, null, rscPath, rscId);
	}

	/**
	 *	生成通用邮件构造对象
	 * @param message	邮件对象
	 * @param to	收件人
	 * @param subject	邮件主题
	 * @param content	邮件正文
	 * @return
	 * @throws MessagingException
	 */
	private MimeMessageHelper getSeniorMessage(MimeMessage message, String to, String subject, String content)
			throws MessagingException {
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(this.from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
		return helper;
	}
	
	/**
	 *	发送高级邮件
	 */
	private void sendSeniorMessage(String to, String subject, String content, String filePath, String rscPath, String rscId) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = this.getSeniorMessage(message, to, subject, content);
			
			// 发送带附件的邮件
			if(filePath != null){
				FileSystemResource file = new FileSystemResource(new File(filePath));
				String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
				helper.addAttachment(fileName, file);
			}
			
			//发送带有静态资源的邮件
			if(rscPath != null || rscId != null) {
				FileSystemResource res = new FileSystemResource(new File(rscPath));
				helper.addInline(rscId, res);
			}

			mailSender.send(message);
			logger.info(new BufferString("发送给").append(to).append("的邮件：").append(subject).append("发送成功！"));
		} catch (MessagingException e) {
			logger.error(new BufferString("发送给").append(to).append("的邮件：").append(subject).append("发送失败！"), e);
		}
	}
	
}