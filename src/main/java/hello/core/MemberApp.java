package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {

        // @Configuration 미사용
//        AppConfig appconfig = new AppConfig();
//        MemberService memberService = appconfig.memberService();

        // ApplicationContext = 스프링컨테이너 (인터페이스)
        // @Configuration가 붙은 AppConfig.class를 구현체로 스프링컨테이너에 등록하고 반환
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 등록된 @Bean 객체 가져오기
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        // 생성자 주입을 활용하지 못한 케이스
//        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());

    }

}
