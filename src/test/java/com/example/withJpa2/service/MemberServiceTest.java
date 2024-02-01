package com.example.withJpa2.service;

import com.example.withJpa2.domain.Address;
import com.example.withJpa2.domain.Member;
import com.example.withJpa2.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional // TEST에서 쓰일 때 자동 록백
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    void 회원_가입() throws Exception{
        Member member = Member.createMember("Jeon", new Address("Gwangju", "seomundaero", "1101"));
        Long saveId = memberService.join(member);
        em.flush();

        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));
    }

    @Test
    void 중복_회원_예외() throws Exception {
        Member member1 = Member.createMember("Jeon", new Address("Gwangu", "namgu", "1111"));
        Member member2 = Member.createMember("Jeon", new Address("aaa", "bbb", "111111"));
        memberService.join(member1);

        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }


}