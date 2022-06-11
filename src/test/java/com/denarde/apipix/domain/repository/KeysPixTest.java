package com.denarde.apipix.domain.repository;

import com.denarde.apipix.domain.entity.KeyPix;
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
public class KeysPixTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    KeysPix repository;

    @Test
    @DisplayName("save keyPix.")
    public void saveKeyPixTest() {

        KeyPix keyPix = createPixCpf();

        KeyPix savedKeyPix = repository.save(keyPix);

        assertThat(savedKeyPix.getId()).isNotNull();

    }


    @Test
    @DisplayName("delete keyPix.")
    public void deleteKeyPixTest() {

        KeyPix keyPix = createPixCpf();
        entityManager.persist(keyPix);
        KeyPix foundKeyPix = entityManager.find( KeyPix.class, keyPix.getId() );

        repository.delete(foundKeyPix);

        KeyPix deletedKeyPix = entityManager.find(KeyPix.class, keyPix.getId());
        assertThat(deletedKeyPix).isNull();

    }

    @Test
    @DisplayName("valid exists keyPix.")
    public void getValidKeyPixExistsTest() {

        KeyPix keyPix = createPixCpf();
        entityManager.persist(keyPix);

        boolean existsKeyPix = repository.existsByKey(keyPix.getKey());

        assertThat(existsKeyPix).isTrue();

    }

    @Test
    @DisplayName("valid not exists keyPix.")
    public void getValidNotKeyPixExistsTest() {

        KeyPix keyPix = createPixCpf();

        boolean existsKeyPix = repository.existsByKey(keyPix.getKey());

        assertThat(existsKeyPix).isFalse();

    }

    @Test
    @DisplayName("return one keyPix by id.")
    public void getKeyPixByIdTest() {

        KeyPix keyPix = createPixCpf();
        entityManager.persist(keyPix);

        Optional<KeyPix> getKeyPix = repository.findById(keyPix.getId());

        assertThat(getKeyPix).isPresent();

    }

    @Test
    @DisplayName("return one keyPix by key.")
    public void getKeyPixByKeyTest() {

        KeyPix keyPix = createPixCpf();
        entityManager.persist(keyPix);

        Optional<KeyPix> getKeyPix = repository.getByKey(keyPix.getKey());

        assertThat(getKeyPix).isPresent();

    }

    @Test
    @DisplayName("return all keyPix.")
    public void getAllKeyPixTest() {

        KeyPix keyPixCpf = createPixCpf();
        KeyPix keyPixEmail = createPixEmail();
        entityManager.persist(keyPixCpf);
        entityManager.persist(keyPixEmail);

        List<KeyPix> keysPix = repository.findAll();

        assertThat(keysPix).hasSize(2);

    }


    private KeyPix createPixCpf() {
        return KeyPix.builder().key("41634836839").keyType(KeyType.CPF).build();
    }

    private KeyPix createPixEmail() {
        return KeyPix.builder().key("luiz_denarde@hotmail.com").keyType(KeyType.EMAIL).build();
    }



}
