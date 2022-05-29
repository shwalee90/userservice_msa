package com.example.userservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseOrders {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Date createdAt;
    private String orderId;
}
