## HTTPS using Self-Signed Certificate in Spring Boot

###  Generating a Self-Signed Certificate
Self-Signed Certificate formats:
1. PKCS12: Public Key Cryptographic Standards is a password protected format that can contain multiple certificates and keys; it's an industry-wide used format.
2. JKS: Java KeyStore is similar to PKCS12; it's a proprietary format and is limited to the Java environment.

#### Generating a Keystore
We can use the following command to generate our PKCS12 keystore format:
```bash
keytool -genkeypair -alias testkeystore -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore testkeystore.p12 -validity 3650
```

KeyStore working with localhost "for testing"
```bash
keytool -genkeypair -keyalg RSA -keysize 2048 -alias testkeystore -dname "CN=mykey,OU=mycomp,O=mycomp,C=GR" -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -keystore testkeystore.p12 -storepass password -keypass password -deststoretype pkcs12
```
We can store any number of key-pairs in the same keystore, with each identified by a unique alias.

For generating our keystore in a JKS format, we can use the following command:
```bash
keytool -genkeypair -alias testkeystore -keyalg RSA -keysize 2048 -keystore testkeystore.jks -validity 3650
```
It is recommended to use the PKCS12 format, which is an industry standard format. So in case we already have a JKS keystore, we can convert it to PKCS12 format using the following command:
```bash
keytool -importkeystore -srckeystore testkeystore.jks -destkeystore testkeystore.p12 -deststoretype pkcs12
```
We'll have to provide the source keystore password and also set a new keystore password. The alias and keystore password will be needed later.

#### Enabling HTTPS in Spring Boot
Spring Boot provides a set of a declarative server.ssl.* properties. We'll use those properties in our sample application to configure HTTPS.

We'll start from a simple Spring Boot application with Spring Security that contains a welcome page handled by the “/welcome” endpoint.

Then we'll copy the file named “testkeystore.p12,” generated in the previous step, into the “src/main/resources/keystore” directory.

### Invoking an HTTPS URL - Test
Implementation can be found under test section

### How to Import a .cer Certificate Into a Java KeyStore

#### Import Certificate
```bash
keytool -importcert -alias mycert_public_cert -file mycert.cer -keystore testkeystore
```
Pass password in command and bypass confirmation (This comes especially handy when running keytool from a script):
```bash
keytool -importcert -alias mycert_public_cert -file mycert.cer -keystore testkeystore -storepass password -noprompt
```
If the KeyStore doesn't exist, it'll be automatically generated. In this case, we can set the format through the storetype argument. 
If not specified, the KeyStore format defaults to JKS if we're using Java 8 or older. From Java 9 on it defaults to PKCS12.

```bash
keytool -importcert -alias mycert_public_cert -file mycert.cer -keystore testkeystoree -storetype PKCS12
```

#### Export .cer
List of keystore entries:
```bash 
keytool -list -v -keystore "testkeystore.p12" -storepass password
```

Export .cer of an entry based on alias name:
```bash
keytool -export -keystore testkeystore.p12 -alias testkeystore -file testkeystore.cer 
```