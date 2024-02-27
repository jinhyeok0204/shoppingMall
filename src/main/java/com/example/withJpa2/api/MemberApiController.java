package com.example.withJpa2.api;

import com.example.withJpa2.domain.Address;
import com.example.withJpa2.domain.Member;
import com.example.withJpa2.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

//    /* Member Entity를 밖으로 유출하는 꼴이 됨. 유지보수에도 좋지 않음 DTO를 만들어 사용하자*/
//    @PostMapping("/api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id, member.getName(), member.getAddress());
//    }
    /* 멤버 등록 버전2*/
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = Member.createMember(request.getName(), request.getAddress());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    /* 멤버 업데이트*/
    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request)
    {
        memberService.update(id, request.getName(), request.getAddress());
        // 커맨드와 쿼리를 분리.. 유지보수성 증가
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(id, findMember.getName(), findMember.getAddress());
    }

    /*멤버 조회 버전 1*/
    @GetMapping("api/v2/members")
    public Result<List<MemberDto>> membersV2(){
        List<Member> memberList = memberService.findAll();
        List<MemberDto> memberDtoList = memberList.stream().map(m -> new MemberDto(m.getName(), m.getAddress()))
                  .toList();
        return new Result<>(memberDtoList);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
        private Address address;
    }
    @Data
    static class UpdateMemberRequest{
        private Long id;

        @NotEmpty(message = "이름은 필수입니다.")
        @Size(min=1, max=10, message = "이름은 1~10 이여야합니다.")
        private String name;

        @Valid
        private Address address;
    }
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
        private Address address;
    }
    @Data
    static class CreateMemberRequest{
        @NotEmpty(message = "이름은 필수입니다.")
        @Size(min=1, max=10, message = "이름은 1~10 이여야합니다.")
        private String name;

        @Valid
        private Address address;
    }
    @Data
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
    }
}
