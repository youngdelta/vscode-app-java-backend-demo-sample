package web.example.app.utils;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JasyptEncryptUtil {

    private static StringEncryptor stringEncryptor;
    
    @Autowired
    public JasyptEncryptUtil(StringEncryptor stringEncryptor) {
        JasyptEncryptUtil.stringEncryptor = stringEncryptor;
    }



    /**
     * 
     */
    public static String encrypt(String text) {
        if (!StringUtils.hasText(text)) return null;

        return stringEncryptor.encrypt(text);
    }

    /**
     * 
     */
	public static String decrypt(String encrypt) {
		if (!StringUtils.hasText(encrypt)) return null;

        return stringEncryptor.decrypt(encrypt);
	}
}