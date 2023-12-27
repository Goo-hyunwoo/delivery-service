package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    // 1. DB에서 계정 확인
    // 2. DB에서 가져온 비밀번호와 사용자 입력 비밀번호로 비교
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var storeUserEntity = storeUserService.getRegisterUser(username);
        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
                storeUserEntity.get().getStoreId(), StoreStatus.REGISTERED);
        log.info("{}: {}", username, storeEntity);
        return storeUserEntity.map(it -> {
                    var userSession = UserSession.builder()
                            .userId(it.getId())
                            .email(it.getEmail())
                            .password(it.getPassword())
                            .status(it.getStatus())
                            .role(it.getRole())
                            .registeredAt(it.getRegisteredAt())
                            .lastLoginAt(it.getLastLoginAt())
                            .unregisteredAt(it.getUnregisteredAt())
                            .storeId(storeEntity.get().getId())
                            .storeName(storeEntity.get().getName())
                            .build();
                    return userSession;
                })
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
