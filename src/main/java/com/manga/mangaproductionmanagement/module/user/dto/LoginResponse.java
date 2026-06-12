package com.manga.mangaproductionmanagement.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Integer userId;
    private String fullName;
    private String email;
}
