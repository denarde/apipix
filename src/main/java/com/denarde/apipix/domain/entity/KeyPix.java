package com.denarde.apipix.domain.entity;

import com.denarde.apipix.domain.enums.KeyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KeyType keyType;
}
