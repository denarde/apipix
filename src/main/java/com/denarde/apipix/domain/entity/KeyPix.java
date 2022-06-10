package com.denarde.apipix.domain.entity;

import com.denarde.apipix.domain.enums.KeyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class KeyPix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "{field.key.mandatory}")
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "{field.keyType.mandatory}")
    private KeyType keyType;
}
