package com.ssafy.pjt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy
@MapperScan(basePackages = { "com.ssafy.**.model.mapper" })
public class WhereIsSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhereIsSpringApplication.class, args);
	}

// TODO 다하고 세션인터셉터 처리하기
//	@Autowired
//	private SessionInterceptor sessionInterceptor;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(sessionInterceptor)
//			.addPathPatterns(Arrays.asList("/assets/*"))
//			.excludePathPatterns(Arrays.asList("/assets/index.html"));
//	}
}
