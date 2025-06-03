package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {   // Test는 한글로 적어도 상관 X

        // given 문법 (준비: 테스트하기 전에 주어진 상황, 초기 상태, 필요한 데이터나 환경을 세팅)
        Member member = new Member();
        member.setName("hello");

        // when 문법 (행동: 실제로 테스트할 대상 메서드나 기능 실행)
        Long saveId = memberService.join(member);

        // then 문법 (검증: 실행 결과가 기대한 대로 나왔는데 검증)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {

        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    /*         memberService.join(member1); "이미 존재하는 회원입니다"으로 잡혀버림 but, try catch+fail 문으로 수정 가능
        try {
            memberService.join(member2);
            fail();
        }
        catch(IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
    */

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}