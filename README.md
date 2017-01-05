AWS SES Example
===============

Amazon Web Services Simple Email Service example.

## Usage

```
mvn clean install
java -jar -Dproperties=<path to properties file>
```

### Example Properties File

```
aws.accessKey=<public key>
aws.secretKey=<secret key>

email.to=foo@bar.net
email.from=bar@baz.org
email.subject=test subject
email.body=test body
```
