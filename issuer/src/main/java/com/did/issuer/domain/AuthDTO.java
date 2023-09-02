package com.did.issuer.domain;

import lombok.Data;

@Data
public class AuthDTO {
    private String name;
    private String phone;
    private String authNum;
    private String publicKey;
}
