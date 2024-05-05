package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// 아래의 방법들은 거의 사용하지 않는 방법
// InitializingBean : 초기화 빈
// DisposableBean : 종료 빈
//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient {

    private String url;

    public NetworkClient() {
        // setter로 필드 주입시 생성단계에서는 url이 부재
        System.out.println("생성자 호출, url : " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서버 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + ", message : " + message);
    }

    // 서버 종료시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }

    // InitializingBean 오버라이딩
    // 의존관계 주입이 끝나면 실행되는 메서드
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메세지");
//    }

    // DisposableBean 오버라이딩
    // 빈이 종료될때 실행되는 메서드
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }

    // 의존관계 주입이 끝나면 실행되는 메서드
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    // 빈이 종료될때 실행되는 메서드
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }

}
