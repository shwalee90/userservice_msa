package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrders;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Date createAt;

    private String encryptedPwd;

    private List<ResponseOrders> orders;

}
