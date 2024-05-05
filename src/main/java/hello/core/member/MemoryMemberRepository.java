package hello.core.member;

import org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component("원하는이름으로빈등록가능")
@Component // ComponentScan으로 빈 등록
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);

    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
