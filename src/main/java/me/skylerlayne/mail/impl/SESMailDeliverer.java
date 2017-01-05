package me.skylerlayne.mail.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.amazonaws.services.simpleemail.AWSJavaMailTransport;

import me.skylerlayne.mail.EmailDeliverer;

/**
 * AWS SES Implementation.
 * 
 * @author skylerl
 * @version 0.1.0
 *
 */
public class SESMailDeliverer implements EmailDeliverer {

	private String to;
	private String from;
	private String subject;
	private String body;
	private Session session;

	public SESMailDeliverer(Session session) {
		this.session = session;
	}

	public void send() throws AddressException, MessagingException {

		// Create a new Message
		Message msg = createMessage();

		// Reuse one Transport object for sending all your messages
		// for better performance
		Transport t = new AWSJavaMailTransport(session, null);
		t.connect();
		t.sendMessage(msg, null);

		// Close your transport when you're completely done sending
		// all your messages
		t.close();
	}

	private Message createMessage() throws AddressException, MessagingException {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(to));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		msg.setText(body);
		msg.saveChanges();
		return msg;
	}

	public EmailDeliverer addFrom(String from) {
		this.setFrom(from);
		return this;
	}

	public EmailDeliverer addTo(String to) {
		this.setTo(to);
		return this;
	}

	public EmailDeliverer addSubject(String subject) {
		this.setSubject(subject);
		return this;
	}

	public EmailDeliverer addBody(String body) {
		this.setBody(body);
		return this;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
