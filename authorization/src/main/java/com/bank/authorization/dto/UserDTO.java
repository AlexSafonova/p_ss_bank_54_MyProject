package com.bank.authorization.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private Long profileId;

    @NotNull
    private String password;

    @NotNull
    private String role;



}
