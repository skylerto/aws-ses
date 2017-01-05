package me.skylerlayne.mail;

/**
 * Interface for configuring an Email for delivery.
 * 
 * @author skylerl
 * @version 0.1.0
 *
 */
public interface EmailDeliverer {

	public void setFrom(String from);

	public void setTo(String to);

	public void setSubject(String subject);

	public void setBody(String body);

	public void send();

	public EmailDeliverer addFrom(String from);

	public EmailDeliverer addTo(String to);

	public EmailDeliverer addSubject(String subject);

	public EmailDeliverer addBody(String body);

}
