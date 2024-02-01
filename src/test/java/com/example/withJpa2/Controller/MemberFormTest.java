package com.example.withJpa2.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberFormTest {
    @Test
    public void getter_test(){
        MemberForm memberForm = new MemberForm();
    }

}