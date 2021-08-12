package com.br.springsecurity.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class Credentials implements Serializable {

    private String email;
    private String password;
}
