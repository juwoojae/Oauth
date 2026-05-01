package com.example.backend.member.controller;

import com.example.backend.member.service.MemberService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
