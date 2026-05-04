package com.example.backend.member.service;

import com.example.backend.member.domain.Member;
import com.example.backend.member.domain.SocialType;
import com.example.backend.member.dto.MemberCreateDto;
import com.example.backend.member.dto.MemberLoginDto;
import com.example.backend.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member create(MemberCreateDto memberCreateDto){
        Member member = Member.builder()
                .email(memberCreateDto.getEmail())
                .password(passwordEncoder.encode(memberCreateDto.getPassword()))  //password  를 encoding 한 뒤 DB 에 저장
                .build();
        memberRepository.save(member);
        return member;
    }

    public Member login(MemberLoginDto memberLoginDto){
        Optional<Member> optMember = memberRepository.findByEmail(memberLoginDto.getEmail());
        if(!optMember.isPresent()){
            throw new IllegalArgumentException("email이 존재하지 않습니다.");
        }

        Member member = optMember.get();
        if(!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("password가 일치하지 않습니다.");
        }
        return member;
    }

    /**
     * DB 에 해당 socialId 를 가진 member 가 존재하는지 탐색하기
     */
    public Member getMemberBySocialId(String socialId) {
        Member member = memberRepository.findBySocialId(socialId).orElse(null);
        return member;
    }

    /**
     * 소셜 로그인으로 들어왔을때 회원가입
     */
    public Member createOauth(String socialId, String email, SocialType socialType) {
        Member member = Member.builder()
                .email(email)
                .socialType(socialType)
                .socialId(socialId)
                .build();
        memberRepository.save(member);
        return member;
    }
}