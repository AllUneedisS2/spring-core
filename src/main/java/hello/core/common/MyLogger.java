package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
// 각각의 HTTP 요청마다 하나씩 생성되고 해당 요청이 끝나는 시점에 소멸된다
//@Scope("request")
// request 스코프와 프록시까지 사용하는 경우
// ScopedProxyMode : MyLogger를 상속받은 가짜 객체를 생성하여 주입한다
// 실제로 ac.getBean("myLogger", MyLogger.class) 해보면 가짜가 나온다
// 이 가짜 프록시 빈은 실제 사용시점에만 진짜 MyLogger를 호출한다
// 참고로 이때 가짜 프록시 빈의 스코프는 디폴트인 Singleton
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    // 중간에 들어오도록 setter로 대체
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }

}
