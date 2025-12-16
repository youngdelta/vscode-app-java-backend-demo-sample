package web.example.app;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class JaysptconfigTest {

	@Test
	void jasypt() {
//		String url = "jdbc:oracle:thin:@127.0.0.1:1521/db";
		String url = "jdbc:p6spy:mysql://localhost:3306/world?serverTimezone=UTC";
		String username = "user1";
		String password = "user12!@";
		String mailid = "sample@sample.co.kr";
		String mailpwd = "sample";

		log.info("url = {}", jasyptEnc(url));
		log.info("username = {}", jasyptEnc(username));
		log.info("password = {}", jasyptEnc(password));
		log.info("mailid = {}", jasyptEnc(mailid));
		log.info("mailpwd = {}", jasyptEnc(mailpwd));

		log.info("url = {}, username = {}, password = {}, mailid = {}, mailpwd = {}", jasyptEnc(url),
				jasyptEnc(username), jasyptEnc(password), jasyptEnc(mailid), jasyptEnc(mailpwd));

	}

	public String jasyptEnc(String VALUE) {
		String key = "user12!@";
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();

		config.setPassword(key);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations(1000);
		config.setPoolSize(1);
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");

		encryptor.setConfig(config);
		return encryptor.encrypt(VALUE);
	}

	String encryptKey;

	@Test
	void jasypt2() {
//		encryptKey = System.getProperty("jasypt.encryptor.password");
		encryptKey = "user12!@";

		String url = "jdbc:mysql://localhost:3306/highgarden_db?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
		String username = "user1";
		String password = "user12!@";

		String encryptUrl = jasyptEncrypt(url);
		String encryptUsername = jasyptEncrypt(username);
		String encryptPassword = jasyptEncrypt(password);

		String decryptUrl = jasyptDecryt(encryptUrl);
		String decryptUsername = jasyptDecryt(encryptUsername);
		String decryptPassword = jasyptDecryt(encryptPassword);

		System.out.println("encryptUrl : " + encryptUrl);
		System.out.println("encryptUsername : " + encryptUsername);
		System.out.println("encryptPassword : " + encryptPassword);

		System.out.println("decryptUrl : " + decryptUrl);
		System.out.println("decryptUserName : " + decryptUsername);
		System.out.println("decryptPassword : " + decryptPassword);

		Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
	}

	private String jasyptEncrypt(String input) {
	    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	    encryptor.setAlgorithm("PBEWithMD5AndDES");
	    encryptor.setPassword(encryptKey);
	    return encryptor.encrypt(input);
    }

	private String jasyptDecryt(String input){
	    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	    encryptor.setAlgorithm("PBEWithMD5AndDES");
	    encryptor.setPassword(encryptKey);
	    return encryptor.decrypt(input);
    }
//    출처: https://generalcoder.tistory.com/25 [HighGarden:티스토리]

}
// 출처: https://jong-bae.tistory.com/68 [기록하는 프로그래머:티스토리]