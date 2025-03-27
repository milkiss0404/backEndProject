package com.study.project.account;

import com.study.project.domain.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(@Email @NotBlank String email);

    Account findByEmail(String mail);

    Account findByNickname(String emailOrNickname);
}
