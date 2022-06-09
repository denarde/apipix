package com.denarde.apipix.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReceivedPix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "key_pix_id")
    private KeyPix keyPix;

    @Column
    private Double value;
}
