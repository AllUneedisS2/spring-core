package hello.core.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // ComponentScan으로 빈 등록
@RequiredArgsConstructor // final인 필드를 상대로 자동으로 생성자를 만들어준다 (+Autowired)
public class MemberServiceImpl implements MemberService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // final은 필드에서 직접 입력하거나, 생성자에서만 주입해야 한다
    // 한 번 주입하면 불변하다
    private final MemberRepository memberRepository;

    // @Component로 등록하면 생성자의 의존주입을 따로해야 한다
    // @Autowired는 이를 해결해준다
    // 기본전략은 MemberRepository라는 타입을 조회한다
    // 즉, getBean(MemberRepository.class)와 같은 의미이다
    // 참고로 생성자가 한 개라면 @Autowired는 생략해도 된다
//    @Autowired
//    public MemberServiceImpl(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

