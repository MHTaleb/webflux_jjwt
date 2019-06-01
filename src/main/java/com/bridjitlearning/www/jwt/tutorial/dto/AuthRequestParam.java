package com.bridjitlearning.www.jwt.tutorial.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class AuthRequestParam {
    @NotNull private String username;
    @NotNull private String password;
}