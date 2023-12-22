package org.delivery.db.userorder.enums;

public enum UserOrderStatus {
    REGISTERED("등록"),
    ORDER("주문"),
    ACCEPT("확인"),
    COOKING("요리중"),
    DELIVERY("배달중"),
    RECEIVE("배달완료"),
    UNREGISTERED("해지");

    UserOrderStatus(String description) {
        this.description = description;
    }

    private String description;
}
