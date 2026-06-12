package com.manga.mangaproductionmanagement.module.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // email or full name
    private String password;
}
