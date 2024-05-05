package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component // ComponentScan으로 빈 등록
@RequiredArgsConstructor // final인 필드를 상대로 자동으로 생성자를 만들어준다 (+Autowired)
public class OrderServiceImpl implements OrderService {

    // DIP, OCP 위반
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // DIP, OCP 위반
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // DIP, OCP 위반
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 인터페이스에만 의존하도록 변경
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // @Component로 등록하면 생성자의 의존주입을 따로해야 한다
    // @Autowired는 이를 해결해준다
    // 기본전략은 MemberRepository/DiscountPolicy라는 타입을 조회한다
    // 즉, getBean(MemberRepository.class)/getBean(DiscountPolicy.class)와 같은 의미이다
    // 참고로 생성자가 한 개라면 @Autowired는 생략해도 된다
//    @Autowired
    // @Qualifier의 경우, type으로 빈을 찾는 @Autowired를 보완하는 것이다
    // 같은 타입을 가진 2개 이상의 빈을 검색할때 우선순위를 설정하는 것이다
//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("fixDiscountPolicy") DiscountPolicy discountPolicy) {
    // @MainDiscountPolicy는 직접 커스터마이징한 어노테이션이다
    // 가서 확인해보면 알겠지만 @Qualifier("mainDiscountPolicy")를 포함하였기 때문에 이러한 방법도 가능하다
//    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
