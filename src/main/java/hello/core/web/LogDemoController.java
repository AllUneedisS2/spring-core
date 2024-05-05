package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.inject.Provider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;

    // SpringApplication 실행시 아래의 코드로는 MyLogger라는 객체를 주입받을 수 없다
    // 왜냐하면 MyLogger는 request 스코프이기 때문이다
    // 즉, 클라이언트의 HTTP 요청이 있어야 하는데, SpringApplication 실행 시점에는 요청이 없기 때문이다
//    private final MyLogger myLogger;

    // Provider를 활용하여 일단 DL(Dependency Lookup) 준비만 해놓자
//    private final Provider<MyLogger> myLoggerProvider;

    // ScopedProxyMode를 사용하면 가짜 MyLogger가 생성된다
    // 때문에 HTTP 요청 없이도 SpringApplication이 실행된다
    private final MyLogger myLogger;


    @RequestMapping("log-demo")
    @ResponseBody
    // HttpServletRequest 리퀘스트 정보를 받을 수 있다
    public String logDemo(HttpServletRequest request) {

        // getRequestURL() : URL 정보 가져오기
        String requestURL = request.getRequestURL().toString();

        // GET 요청시에만 Provider를 통해 스코프가 request인 MyLogger 가져오기
//        MyLogger myLogger = myLoggerProvider.get();

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");

        logDemoService.logic("testId");

        return "OK";
    }
}
