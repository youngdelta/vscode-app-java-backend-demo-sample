package web.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
    org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class
})
 @EnableEncryptableProperties //꼭 추가해주도록 한다.
// 출처: https://generalcoder.tistory.com/25 [HighGarden:티스토리]

public class AppApplication {


	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
