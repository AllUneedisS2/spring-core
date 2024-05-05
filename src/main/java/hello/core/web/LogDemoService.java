package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.inject.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    // 마찬가지로 SpringApplication 실행 시점에는 Provider로 DL(Dependency Lookup) 준비만 해야한다
//    private final Provider<MyLogger> myLoggerProvider;

    // ScopedProxyMode 사용하였으므로, request 스코프인 myLogger를 아래와 같이 작성 가능
    private final MyLogger myLogger;

    public void logic(String id) {
//        MyLogger myLogger = myLoggerProvider.get();
        myLogger.log("service id = " + id);
    }
}
