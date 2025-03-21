package com.study.project.account;

import com.study.project.domain.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {

    private Account account;

    public UserAccount(Account account) {
        // 부모 클래스 (User)의 생성자를 호출
        super(account.getNickname(),account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }
}
