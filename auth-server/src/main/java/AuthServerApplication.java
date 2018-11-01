import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qichao
 * @create 2018-11-01
 **/
@SpringBootApplication
@ComponentScan({"com.xiaobu.auth.core","com.xiaobu.auth.server"})
public class AuthServerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AuthServerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
}
