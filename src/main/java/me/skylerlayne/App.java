package me.skylerlayne;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;

import me.skylerlayne.mail.EmailDeliverer;
import me.skylerlayne.mail.impl.SESMailDeliverer;

/**
 * Hello world!
 *
 */
public class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String propertiesFile = System.getProperty("properties", "application.properties");
		log.info("Loading properties file: " + propertiesFile);
		Properties props = new Properties();
		props.load(new FileInputStream(propertiesFile));

		AWSCredentials credentials = getCredentials(props.getProperty("aws.accessKey"),
				props.getProperty("aws.secretKey"));
		AmazonSimpleEmailService client = getService(credentials);
		props.setProperty("mail.transport.protocol", "aws");
		props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
		props.setProperty("mail.aws.password", credentials.getAWSSecretKey());

		Session session = Session.getInstance(props);
		Region REGION = Region.getRegion(Regions.fromName(props.getProperty("aws.region")));
		client.setRegion(REGION);
		String to = props.getProperty("email.to");
		String from = props.getProperty("email.from");
		String subject = props.getProperty("email.subject");
		String body = props.getProperty("email.body");
		verifyEmailAddress(client, from);
		
		String sandbox = System.getProperty("sandbox", "false");
		if(sandbox.equals("true")) {
			verifyEmailAddress(client, to);	
		}

		EmailDeliverer mailer = new SESMailDeliverer(session);
		try {

			log.info("Sending Email to " + to + " from " + from);
			mailer.addTo(to).addFrom(from).addSubject(subject).addBody(body).send();
			log.info("Email successfully delivered");
		} catch (AddressException e) {
			e.printStackTrace();
			log.info("Caught an AddressException, which means one or more of your "
					+ "addresses are improperly formatted.");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.info("Caught a MessagingException, which means that there was a "
					+ "problem sending your message to Amazon's E-mail Service check the "
					+ "stack trace for more information.");
		}

	}

	private static AWSCredentials getCredentials(String accessKey, String secretKey) {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	private static AmazonSimpleEmailService getService(AWSCredentials credentials) {
		return new AmazonSimpleEmailServiceClient(credentials);
	}

	/**
	 * Sends a request to Amazon Simple Email Service to verify the specified
	 * email address. This triggers a verification email, which will contain a
	 * link that you can click on to complete the verification process.
	 *
	 * @param ses
	 *            The Amazon Simple Email Service client to use when making
	 *            requests to Amazon SES.
	 * @param address
	 *            The email address to verify.
	 */
	private static void verifyEmailAddress(AmazonSimpleEmailService ses, String address) {
		ListVerifiedEmailAddressesResult verifiedEmails = ses.listVerifiedEmailAddresses();
		if (verifiedEmails.getVerifiedEmailAddresses().contains(address)) {
			return;
		}

		ses.verifyEmailAddress(new VerifyEmailAddressRequest().withEmailAddress(address));
		log.info("Please check the email address " + address + " to verify it");
		System.exit(0);
	}
}
