package me.skylerlayne.mail;

/**
 * Interface for configuring an Email for delivery.
 * 
 * @author skylerl
 * @version 0.1.0
 *
 */
public interface EmailDeliverer {

	/**
	 * Add the From section of the email.
	 * 
	 * @param from
	 * @return the implemented object for method chaining.
	 */
	public EmailDeliverer addFrom(String from);

	/**
	 * Add the To section of the email.
	 * 
	 * @param to
	 * @return the implemented object for method chaining.
	 */
	public EmailDeliverer addTo(String to);

	/**
	 * Add the email's Subject.
	 * 
	 * @param subject
	 * @return the implemented object for method chaining.
	 */
	public EmailDeliverer addSubject(String subject);

	/**
	 * Add the body of the email.
	 * 
	 * @param body
	 * @return the implemented object for method chaining.
	 */
	public EmailDeliverer addBody(String body);

	/**
	 * Send the email.
	 */
	public void send();

	public void setFrom(String from);

	public void setTo(String to);

	public void setSubject(String subject);

	public void setBody(String body);

}
