package com.denarde.apipix.domain.repository;

import com.denarde.apipix.domain.entity.KeyPix;
import com.denarde.apipix.domain.entity.ReceivedPix;
import com.denarde.apipix.domain.enums.KeyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ReceivedsPixTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ReceivedsPix repository;

    @Test
    @DisplayName("save receivedPix.")
    public void saveReceivedPixTest() {

        ReceivedPix receivedPix = createReceivedPix();

        ReceivedPix savedReceivedPix = repository.save(receivedPix);

        assertThat(savedReceivedPix.getId()).isNotNull();

    }

    private ReceivedPix createReceivedPix() {
        return ReceivedPix.builder().key("41634836839").value(200.50).build();
    }

}
