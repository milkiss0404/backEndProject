package com.study.project.account;

import com.study.project.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("인증 메일 확인 - 인증값 오류 발생시키기")
    @Test
    void checkEmailToken_with_wrong_input() throws Exception{
        mockMvc.perform(get("/check-email-token")
                        .param("email", "shykse@naver.com")
                        .param("token", "wow")
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("account/checked-email"));
    }

    @DisplayName("인증 메일 확인")
    @Test
    void checkEmailToken()throws Exception{

        Account account = Account.builder()
                .email("shykse@naver.com")
                .nickname("우철")
                .password("12345678")
                .build();

        accountRepository.save(account);

        account.generateEmailCheckToken();


        mockMvc.perform(get("/check-email-token")
                .param("email", account.getEmail())
                .param("token", account.getEmailCheckToken()))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("numberOfUser"))
                .andExpect(model().attributeExists("nickname"))
                .andExpect(view().name("account/checked-email"));
    }

    @DisplayName("회원가입 화면 보이는지 테스트")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("signUpFrom"));
    }

    @DisplayName("회원 가입 처리 -입력값 오류")
    @Test
    void signSubmit_with_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "woochul")
                        .param("email", "dsadsa@dsadsa.com")
                        .param("password", "12dasdad3")
                        .with(csrf()))
//                .andExpect(status().isOk()) 리다이렉트는 200이 아니고 302가뜸
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));


        Account account = accountRepository.findByEmail("dsadsa@dsadsa.com");
        assertNotNull(account);
        assertNotEquals(account.getPassword(),"12dasdad3");
    }
}