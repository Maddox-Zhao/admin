package com.huaixuan.network.biz.service.user;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.huaixuan.network.biz.domain.user.MailData;

/**
 * Class for sending e-mail messages based on Velocity templates or with attachments.
 * 
 * @author Matt Raible
 */
@Service("mailEngine")
public class MailEngine implements MailEngineBase {
	private final Log log = LogFactory.getLog(MailEngine.class);
	@Autowired
	private MailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Value("${mail.default.from}")
	private String defaultFrom;
	@Value("${mail.default.personal}")
	private String defaultPersonal;
	@Value("${mail.default.turnOn}")
	private String turnOn;
	private Vector<MailData> mailDataVector;

	public Vector getmailDataVector() {
		return this.mailDataVector;
	}

	/**
	 * Send a simple message based on a Velocity template.
	 * 
	 * @param msg
	 *            the message to populate
	 * @param templateName
	 *            the Velocity template to use (relative to classpath)
	 * @param model
	 *            a map containing key/value pairs
	 */
	public void sendMessage(SimpleMailMessage msg, String templateName, Map model) {
		String result = null;

		try {
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
		} catch (VelocityException e) {
			log.error(e.getMessage());
			log.error(e.getMessage());
		}

		msg.setText(result);
		send(msg);
	}

	/**
	 * Send a simple message with pre-populated values.
	 * 
	 * @param msg
	 *            the message to send
	 * @throws org.springframework.mail.MailException
	 *             when SMTP server is down
	 */
	public void send(SimpleMailMessage msg) throws MailException {
		try {
			if ("true".equalsIgnoreCase(turnOn)) {
				msg.setFrom(defaultFrom);
				mailSender.send(msg);
			} else {
				log.warn("邮件发送开关未打开：" + msg.getTo() + " " + msg.getText());
			}
		} catch (MailException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Convenience method for sending messages with attachments.
	 * 
	 * @param recipients
	 *            array of e-mail addresses
	 * @param sender
	 *            e-mail address of sender
	 * @param resource
	 *            attachment from classpath
	 * @param bodyText
	 *            text in e-mail
	 * @param subject
	 *            subject of e-mail
	 * @param attachmentName
	 *            name for attachment
	 * @throws MessagingException
	 *             thrown when can't communicate with SMTP server
	 */
	public void sendMessage(String[] recipients, String sender, ClassPathResource resource, String bodyText,
			String subject, String attachmentName) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(recipients);

		// use the default sending if no sender specified
		try {
			if (sender == null)
				helper.setFrom(defaultFrom, defaultPersonal);

			else
				helper.setFrom(sender, defaultPersonal);

		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
		helper.setText(bodyText);
		helper.setSubject(subject);

		helper.addAttachment(attachmentName, resource);

		if ("true".equalsIgnoreCase(turnOn)) {
			((JavaMailSenderImpl) mailSender).send(message);
		} else {
			log.warn("邮件发送开关未打开：" + recipients + " " + bodyText);
		}

	}

	public void sendMessage(String recipients, String subject, String templateName, Map model)
			throws MessagingException {

		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "GBK");

		helper.setTo(recipients);

		String result = null;
		try {
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "GBK", model);
		} catch (VelocityException e) {
			// log.error(e.getMessage());
			log.error(e.getMessage());
		}
		helper.setText(result, true);
		helper.setSubject(subject);
		try {
			helper.setFrom(defaultFrom, defaultPersonal);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}

		if ("true".equalsIgnoreCase(turnOn)) {
			((JavaMailSenderImpl) mailSender).send(message);
		} else {
			log.warn("邮件发送开关未打开：" + recipients + " " + result);
		}

	}

	public void sendMessage(String recipients, String subject, DataSource ds, String templateName, Map model)
			throws MessagingException {

		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "GBK");

		helper.setTo(recipients);

		String result = null;
		try {
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "GBK", model);
		} catch (VelocityException e) {
			// log.error(e.getMessage());
			log.error(e.getMessage());
		}
		helper.setText(result, true);
		helper.setSubject(subject);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		helper.addAttachment("Warning_" + df.format(new Date()) + ".xls", ds);
		try {
			helper.setFrom(defaultFrom, defaultPersonal);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}

		if ("true".equalsIgnoreCase(turnOn)) {
			((JavaMailSenderImpl) mailSender).send(message);
		} else {
			log.warn("邮件发送开关未打开：" + recipients + " " + result);
		}

	}

	public void sendMessage(String[] recipients, String subject, String templateName, Map model)
			throws MessagingException {

		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "GBK");

		helper.setTo(recipients);

		String result = null;
		try {
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "GBK", model);
		} catch (VelocityException e) {
			log.error(e.getMessage());
			log.error(e.getMessage());
		}
		helper.setText(result, true);
		helper.setSubject(subject);
		try {
			helper.setFrom(defaultFrom, defaultPersonal);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}

		if ("true".equalsIgnoreCase(turnOn)) {
			((JavaMailSenderImpl) mailSender).send(message);
		} else {
			log.warn("邮件发送开关未打开：" + recipients + " " + result);
		}

	}

	public void setTurnOn(String turnOn) {
		this.turnOn = turnOn;
	}

	@SuppressWarnings("unchecked")
	public void setMailData(MailData mailData) {
		if (this.mailDataVector == null) {
			this.mailDataVector = new Vector();
		}
		this.mailDataVector.add(mailData);
	}

	/**
	 * 异步发送邮件时候线程启动
	 * 
	 * @author chenyan 2009/12/10
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		Vector mailDataVector = getmailDataVector();
		while (!mailDataVector.isEmpty()) {
			MailData mailData = (MailData) mailDataVector.get(0);
			mailDataVector.remove(mailData);
			try {
				if (mailData != null) {
					sendMessage(mailData.getToAddress(), mailData.getSubject(), mailData.getMailId(),
							mailData.getData());
				}
			} catch (Exception e) {
				log.error("Send mail failure. Address:" + mailData.getToAddress() + e);
			}
		}
	}
	// public MailEngine getMailEngine() {
	// return this.getMailEngine();
	// }
}
