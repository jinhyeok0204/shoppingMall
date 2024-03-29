package com.example.withJpa2.service;

import com.example.withJpa2.domain.Address;
import com.example.withJpa2.domain.Member;
import com.example.withJpa2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //전체 조회
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    @Transactional
    public void update(Long id, String name, Address address) {
        Member member = memberRepository.findOne(id);
        member.changeName(name);
        member.changeAddress(address);
    }
}
