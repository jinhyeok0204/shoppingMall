package com.example.withJpa2.Controller;

import com.example.withJpa2.domain.Address;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberForm {

    @NotEmpty(message = "회원 이름이 필수입니다")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}