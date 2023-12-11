package org.delivery.api.account;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.db.account.AccountEntity;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public Api<AccountMeResponse> me() {
        var response = AccountMeResponse.builder()
                .email("abaccd@namver.com")
                .name("홍")
                .registeredAt(LocalDateTime.now())
                .build();

        return Api.OK(response);
    }
    @GetMapping("/notme")
    public Api<Object> notme() {
        var response = AccountMeResponse.builder()
                .email("abaccd@namver.com")
                .name("홍")
                .registeredAt(LocalDateTime.now())
                .build();

        return Api.ERROR(UserErrorCode.USER_NOT_FOUND, "홍길동 사용자 없음");
    }
}
