package com.autocreate;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author Mr_Yang
 * @version 创建时间：2012-3-29 下午03:13:31
 * 
 */

public class SpringSendMail
{
	public static void main(String[] args) throws Exception
	{

		JavaMailSenderImpl senderMail = new JavaMailSenderImpl();
		
		// 设定 Mail Server
		senderMail.setHost("mail.huaixuan.com.cn");
		senderMail.setPort(25);

		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");

		// SMTP验证时，需要用户名和密码
		senderMail.setUsername("iGoldcane@163.com");
		senderMail.setPassword("123456");
		senderMail.setJavaMailProperties(prop);

		// 建立简单的邮件信息
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		// 设定收件人、寄件人、主题与内文
		mailMessage.setTo("yjkki@126.com");
		mailMessage.setFrom("iGoldcane@163.com");
		mailMessage.setSubject("小子");
		mailMessage.setText("小子想死吧。。。。。。!!");

		// 传送邮件
		senderMail.send(mailMessage);
		
		
		

		//发送HTML格式的邮件		
		//建立邮件信息，可发送HTML格式
		MimeMessage mimeMessage = senderMail.createMimeMessage(); //MimeMessage-->java的
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"GBK"); // MimeMessageHelper-->spring的  不加后面两个参数会乱码

		//设置收件人，主题，内容
		messageHelper.setSubject("Hello! ");
		messageHelper.setFrom("iGoldcane@163.com");
		messageHelper.setTo("yjkki@126.com");
		
		StringBuffer str = new StringBuffer();
		str.append("<html><head></head><body><h1>Hello! 中文! </h1></body></html>");
		messageHelper.setText(str.toString(),true); //为true-->发送转义HTML

		//senderMail.send(mimeMessage);  //这个是不带附件的	
		
		//发送带附件的
		FileSystemResource file = new FileSystemResource(new File("E:\\DevelopmentSoft\\spring-framework-3.0.5.RELEASE\\docs\\javadoc-api\\index.html"));
		messageHelper.addAttachment("index.html", file);
		
		senderMail.send(mimeMessage); //这个是发送带附件的

		System.out.println("邮件传送OK..");
	}
}
