package me.skylerlayne.mail.impl;

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

	public void send() {
		// TODO Auto-generated method stub

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
