package com.study.project.account.validator;

import com.study.project.account.AccountRepository;
import com.study.project.account.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return SignUpForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {  //에러가나면 error 파라미터가 들어옴
        SignUpForm signUpForm = (SignUpForm) target;
        if (accountRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()},"이미 존재하는 이메일입니다");
        }

        if (accountRepository.existsByNickname(signUpForm.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpForm.getNickname()},"이미 존재하는 닉네임입니다.");
        }
    }
}
