package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링 컨테이너에서 빈을 관리할 수 있도록 도와주는 어노테이션
// @Bean들의 싱글톤을 보장해줌
// AppConfig.class를 상속받은 클래스를 빈으로 등록하게 함
@Configuration
public class AppConfig {

    @Bean // 스프링컨테이너에 등록
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    // MemoryMemberRepository()는 memberService()에서 한 번, OrderService()에서 한 번, 이렇게 총 두 번 new 된다
    // 하지만 이때 MemoryMemberRepository()는 같은 @Bean이다
    // 이유는 스프링 컨테이너가 한 번만 호출되도록 관리해주기 때문이다
    // 테스트 코드는 ConfigurationSingletonTest에서 확인 가능하다
    @Bean
    public MemberRepository memberRepository() {
        // AppConfig.class에 @Configuration을 붙이지 않으면 3번 호출됨 (new 될 때마다 호출)
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }

}
