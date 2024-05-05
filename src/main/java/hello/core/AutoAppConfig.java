package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component 붙은 애들을 자동으로 스캔해서 @Bean으로 등록
// @Configuration이 붙은 클래스도 자동 스캔 대상이므로 예제를 위해 일단 필터링
/*
기본 스캔 대상은 아래와 같다
@Component
@Controller
@Service
@Repository
@Configuration
*/
@ComponentScan(
        // 해당 패키지에만 존재하는 @Component를 스캔한다
        // 이렇게 안해놓으면 모든 자바를 다뒤지기 때문에 오래걸린다
//        basePackages = "hello.core.member",
        // 해당 클래스가 존재하는 패키지내 존재하는 @Component를 스캔한다
        // 하지만 이것은 사실 default 값이기도 하다
        // 그렇기 때문에 관례적으로 @Configuration파일은 시작 루트에 만든다
//        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // 컴포넌트 스캔으로 자동으로 등록된 빈과 이름이 같은 빈을 수동으로 등록하였다
    // 이러한 경우, 수동으로 등록한 빈이 오버라이딩한다
    // 하지만 부트 기준으로, 이러한 경우, 오류가 발생하도록 기본 값이 설정되어 있다
    // @SpringBootApplication 돌려보면 알 수 있다
    // 부트에서도 수동으로 등록한 빈이 자동으로 오버라이딩 되게하려면 properties파일에 아래와 같이 입력하면 되긴한다
    // spring.main.allow-bean-definition-overriding=true
//    @Bean("memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}