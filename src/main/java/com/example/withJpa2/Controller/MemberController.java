package com.example.withJpa2.Controller;

import com.example.withJpa2.domain.Address;
import com.example.withJpa2.domain.Member;
import com.example.withJpa2.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm) {

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        log.info(memberForm.getName());
        Member member = Member.createMember(memberForm.getName(), address);
        memberService.join(member);

        return "redirect:/";
    }
}
