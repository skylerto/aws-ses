AWS SES Example
===============

Amazon Web Services Simple Email Service example.

## Usage

### Script Helper

#### Help
```
./bin/aws-ses
```

#### Build
```
./bin/aws-ses build
```

#### Execute
```
./bin/aws-ses run <path to properties file> <optional true/false sandbox argument>
```

### DIY
```
mvn clean install
java -jar -Dproperties=<path to properties file> -Dsandbox=<optional true/false sandbox argument>
```

### Example Properties File

```
aws.accessKey=<public key>
aws.secretKey=<secret key>
aws.region=<region>

email.to=foo@bar.net
email.from=bar@baz.org
email.subject=test subject
email.body=test body
```

## Gotchas

Be aware that you must confirm *both* the sender and recipient email addresses under a sandbox environment.
You can enable [Production Mode](http://docs.aws.amazon.com/ses/latest/DeveloperGuide/request-production-access.html) so recipients do not need to verify their address.

