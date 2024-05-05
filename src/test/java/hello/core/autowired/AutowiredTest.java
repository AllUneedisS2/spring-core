package hello.core.autowired;

import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        // default 값은 @Autowired(required = true)이다
        // 때문에 빈에 등록되어있는 Member가 존재해야한다
        // 하지만 Member는, 단순 Dto로, 빈으로 등록하지 않았으므로 에러가 발생한다
        // required를 false로 지정하면 자동 주입할 대상이 없으면 세터 메서드 자체가 호출이 되지 않는다
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        // 자동 주입할 대상이 없으면 null이 입력된다
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        // 자동 주입할 대상이 없으면 Optional.empty가 입력된다
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }


}
