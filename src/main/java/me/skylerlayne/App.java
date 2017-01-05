package me.skylerlayne;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;

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
		String accessKey = props.getProperty("aws.accessKey");
		log.info("Using Access Key: " + accessKey);
		String secretKey = props.getProperty("aws.secretKey");
		log.info("Using Private Key: " + secretKey);
		String to = props.getProperty("email.to");
		String from = props.getProperty("email.from");
		String subject = props.getProperty("email.subject");
		String body = props.getProperty("email.body");

		EmailDeliverer mailer = new SESMailDeliverer(getService(getCredentials(accessKey, secretKey)));
		mailer.addTo(to).addFrom(from).addSubject(subject).addBody(body).send();

	}

	private static AWSCredentials getCredentials(String accessKey, String secretKey) {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	private static AmazonSimpleEmailService getService(AWSCredentials credentials) {
		return new AmazonSimpleEmailServiceClient(credentials);
	}
}
