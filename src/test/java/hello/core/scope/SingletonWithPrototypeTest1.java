package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;


import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    // 각각의 prototypeBean을 호출하였기 때문에 당연히 각각의 count는 1씩 증가
    @Test
    void prototypeFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    // singleton인 ClientBean에 한 번 주입된 PrototypeBean은 prototype이어도 새로 조회되지 않는다
    // 때문에 count는 계속해서 기존의 count 값에서 1씩 증가한다
    // 즉, prototype이 singleton에 속하게 되는 것이다
    // singleton은 한 번 생성해서 던져버리고 더 이상 컨테이너가 관리하지 않기 때문이다
    @Test
    void singletonClientUsePrototype() {

        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {

        // 생성시점에 한 번 주입
        // prototype 일지라도, 값이 유지된다
//        private final PrototypeBean prototypeBean;

        // 등록되어 있는 빈을 찾아 제공해주는 ObjectProvider
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        // ObjectProvider와 비슷한 역할의 자바 표준 객체
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        // private final로 값 넣을때 사용
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {

            // ClientBean을 매번 생성할때마다 ObjectProvider로 prototypeBean을 새로 불러온다
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();

            // 자바 표준 Provider 객체를 활용한 케이스
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }

    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        // prototype이라 호출시 동작 안함
        // 하지만 prototype을 주입받은 ClientBean에서 임의로 동작 가능
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy " + this);
        }

    }
}
