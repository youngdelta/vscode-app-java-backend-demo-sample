package web.example.app;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;
import web.example.app.utils.JasyptEncryptUtil;

@Log4j2
@SpringBootTest
class AppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    @DisplayName("1. Encrypt Test")
    void test1() {
        String secretKey = "ThisIsTestSecretKey";

        String targetText = "This Is Target Text!!";

        // Using Jasypt
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretKey);
        encryptor.setAlgorithm("PBEWithMD5AndDES"); //Default

        String encryptedText = encryptor.encrypt(targetText);
        // System.out.println("encryptedText = " + encryptedText);
        log.info("encryptedText = {}", encryptedText);

        String decryptedText = encryptor.decrypt(encryptedText);
        // System.out.println("decryptedText = " + decryptedText);
        log.info("decryptedText = {}", decryptedText);

        Assertions.assertThat(decryptedText).isEqualTo(targetText);
    }

    @Test
    @DisplayName("3. Encrypt Util Test")
    void test3() {
        String targetText = "This is a secret message";
        String encrypt = JasyptEncryptUtil.encrypt(targetText);
        // System.out.println("encrypt = " + encrypt);
        log.info("encrypt = {}", encrypt);

        String decrypt = JasyptEncryptUtil.decrypt(encrypt);
        // System.out.println("decrypt = " + decrypt);
        log.info("decrypt = {}", decrypt);


        Assertions.assertThat(decrypt).isEqualTo(targetText);
    }

}
