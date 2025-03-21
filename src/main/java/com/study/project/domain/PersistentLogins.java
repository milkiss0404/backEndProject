package com.study.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "persistent_logins")  // Spring Security에서 사용하는 기본 테이블명
@Getter
@Setter
public class PersistentLogins {

    @Id
    @Column(length = 64)
    private String series;  // 고유 식별자

    @Column(nullable = false, length = 64)
    private String username;  // 사용자명

    @Column(nullable = false, length = 64)
    private String token;  // Remember-Me 토큰

    @Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsed;  // 마지막 사용 시간
}
