package com.example.crud.modules.auth.entities;

import com.example.crud.modules.auth.dto.PinDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pin")
@Getter
@Setter
@AllArgsConstructor
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String pin;

    @NotNull
    @NotBlank
    private String type;

    @NotNull
    @NotBlank
    private String email;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public Pin() {
    }

    public Pin(PinDto pinDto) {
        this.pin = pinDto.getPin();
        this.type = pinDto.getType();
        this.email = pinDto.getEmail();
    }
}
