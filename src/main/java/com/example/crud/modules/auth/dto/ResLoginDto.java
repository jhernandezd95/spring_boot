package com.example.crud.modules.auth.dto;

import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
public class ResLoginDto implements Serializable {

    @NotNull
    UserData user;

    @NotNull
    @NotBlank
    private final String token;

    public ResLoginDto(User user, String token) {
        this.user = new UserData(
                user.getId(),
                user.getEmail(),
                user.getBirthDay(),
                user.getRoles(),
                user.getLastLogin(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired()
        );
        this.token = "Bearer " + token;
    }
}

@Data
@AllArgsConstructor
class UserData implements Serializable {
    @NotNull
    private final Long id;

    @Email
    @NotNull
    @NotBlank
    private final String email;

    @Past
    private final Date birthDay;

    @NotNull
    @NotEmpty
    private final Collection<Role> roles;

    @Past
    private Date lastLogin;

    @NotNull
    @AssertTrue
    private Boolean isEnable;

    @NotNull
    @AssertFalse
    private Boolean isAccountNonLocked;

    @NotNull
    @AssertFalse
    private Boolean isAccountNonExpired;
}
