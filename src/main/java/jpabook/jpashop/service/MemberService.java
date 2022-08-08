package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    // memberRepository를 RequiredArgsConstructor를 사용해서 자동으로 초기화
    private final MemberRepository memberRepository;

    /*
    회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void validateDuplicateMember(Member member) {
        // EXCEPTION. Member의 name을 unique로 설정하기 권장
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    // 회원 전체 조회. 조회할 때 성능 상의 이점이 있음.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberID) {
        return memberRepository.findOne(memberID);
    }
}
