package hong.CashGuard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@EnableScheduling
@SpringBootApplication
public class CashGuardApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CashGuardApplication.class, args);
		Environment environment = applicationContext.getEnvironment();
		String serverPort = environment.getProperty("server.port");
		log.info("======================= Server is running on port: {} ===========================", serverPort);
	}

}
