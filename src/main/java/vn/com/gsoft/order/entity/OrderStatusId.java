package vn.com.gsoft.order.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatusId {
    UNDEFINED(-1L),
    ALL(0L),
    BUYER_NEW(1L),
    BUYER_CANCELED(2L),
    BUYER_APPROVAL_AND_WAITING_TO_SEND_THE_ORDER(3L),
    BUYER_WAITING_FOR_ACCEPT_CHANGES_FROM_SELLER(4L),
    BUYER_RECEIVED(5L),
    SELLER_NEW(20L),
    SELLER_CANCELED(21L),
    SELLER_APPROVAL_AND_WAITING_TO_SEND_THE_ORDER(22L),
    SELLER_CHANGES_ACCEPTED_BY_BUYER(23L),
    SELLER_PACKAGE(24L),
    SELLER_SHIPPING(25L),
    SELLER_RETURNED_BY_BUYER(26L),
    SELLER_HANDLING_BY_SELLER(27L),
    COMPLETED(40L),
    SPLIT_ORDER(100L),
    SELLER_WAITING_APPROVAL_BY_SUPERVISOR(101L),
    ORDER_UPDATED(102L),
    ;

    private final Long value;


    OrderStatusId(Long value) {
        this.value = value;
    }

    @JsonValue
    public Long getValue() {
        return value;
    }

    @JsonCreator
    public static OrderStatusId fromValue(Long value) {
        for (OrderStatusId v : OrderStatusId.values()) {
            if (v.getValue() == value) {
                return v;
            }
        }
        return null;
    }
}
