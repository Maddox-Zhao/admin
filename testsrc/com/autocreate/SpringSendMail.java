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
 * @version ����ʱ�䣺2012-3-29 ����03:13:31
 * 
 */

public class SpringSendMail
{
	public static void main(String[] args) throws Exception
	{

		JavaMailSenderImpl senderMail = new JavaMailSenderImpl();
		
		// �趨 Mail Server
		senderMail.setHost("mail.huaixuan.com.cn");
		senderMail.setPort(25);

		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");

		// SMTP��֤ʱ����Ҫ�û���������
		senderMail.setUsername("iGoldcane@163.com");
		senderMail.setPassword("123456");
		senderMail.setJavaMailProperties(prop);

		// �����򵥵��ʼ���Ϣ
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		// �趨�ռ��ˡ��ļ��ˡ�����������
		mailMessage.setTo("yjkki@126.com");
		mailMessage.setFrom("iGoldcane@163.com");
		mailMessage.setSubject("С��");
		mailMessage.setText("С�������ɡ�����������!!");

		// �����ʼ�
		senderMail.send(mailMessage);
		
		
		

		//����HTML��ʽ���ʼ�		
		//�����ʼ���Ϣ���ɷ���HTML��ʽ
		MimeMessage mimeMessage = senderMail.createMimeMessage(); //MimeMessage-->java��
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"GBK"); // MimeMessageHelper-->spring��  ���Ӻ�����������������

		//�����ռ��ˣ����⣬����
		messageHelper.setSubject("Hello! ");
		messageHelper.setFrom("iGoldcane@163.com");
		messageHelper.setTo("yjkki@126.com");
		
		StringBuffer str = new StringBuffer();
		str.append("<html><head></head><body><h1>Hello! ����! </h1></body></html>");
		messageHelper.setText(str.toString(),true); //Ϊtrue-->����ת��HTML

		//senderMail.send(mimeMessage);  //����ǲ���������	
		
		//���ʹ�������
		FileSystemResource file = new FileSystemResource(new File("E:\\DevelopmentSoft\\spring-framework-3.0.5.RELEASE\\docs\\javadoc-api\\index.html"));
		messageHelper.addAttachment("index.html", file);
		
		senderMail.send(mimeMessage); //����Ƿ��ʹ�������

		System.out.println("�ʼ�����OK..");
	}
}
