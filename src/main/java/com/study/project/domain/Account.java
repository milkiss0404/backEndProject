package com.study.project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified; //이메일 계정이 인증계정인지 아닌지 판별

    private String emailCheckToken; //이메일 검증한 토큰값?

    private LocalDateTime joinedAt;

    private String bio; //자기소개

    private String url;

    private String occupation; //하는일

    private String location;

    @ManyToMany
    private Set<Zone> zones = new HashSet<>();

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @Lob
    @Basic(fetch = FetchType.EAGER) //프로필 이미지를 떄마다 가져오려고 했다고함
    private String profileImage;

    private boolean studyCreatedByEmail; //스터디가 만들어진걸 이메일로 받을것인가

    private boolean studyCreatedByWeb = true; //스터디가 만들어진걸 웹으로 받을것인가

    private boolean studyEnrollmentResultByEmail; // 가입신청 결과를 이메일로 받을것인가

    private boolean studyEnrollmentResultByWeb = true;;// 가입신청 결과를 웹으로 받을것인가

    private boolean studyUpdatedByEmail; //스터디의 갱신된 정보들을 이메일로받을것인가

    private boolean studyUpdatedByWeb; //스터디의 갱신된 정보들을 웹로받을것인가

    private LocalDateTime emailCheckTokenGeneratedAt;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();  //여기서 this 는 account 객체 겠죠? 이정도 센스는 있어야지
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusSeconds(1));
    }
}