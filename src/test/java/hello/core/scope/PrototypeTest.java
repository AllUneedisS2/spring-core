package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

// 프로토타입은 매번 빈을 요청/조회 할때마다 새롭게 생성된다
// 생성/의존관계주입/초기화 까지만 관여를 하고 더는 관리하지 않는다
// 때문에 @PreDestroy같은 메서드가 실행되지 않는다
public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        // 사실 @ComponentScan이나 @Config 없이 바로 빈 등록이 가능하긴함
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
        // prototype에서는 동작하지 않음
        ac.close();
        // 직접 close가 가능하기는 함
        prototypeBean1.close();
        prototypeBean2.close();
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("SingletonBean.close");
        }

    }

}
