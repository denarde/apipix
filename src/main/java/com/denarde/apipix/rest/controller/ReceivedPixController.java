package com.denarde.apipix.rest.controller;

import com.denarde.apipix.domain.entity.KeyPix;
import com.denarde.apipix.domain.entity.ReceivedPix;
import com.denarde.apipix.domain.repository.KeysPix;
import com.denarde.apipix.domain.repository.ReceivedsPix;
import com.denarde.apipix.rest.dto.ReceivedPixDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/receivedPix")
public class ReceivedPixController {

    @Autowired
    private ReceivedsPix receivedsPixrepository;

    @Autowired
    private KeysPix keysPixRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReceivedPixDTO save(@RequestBody @Valid ReceivedPixDTO receivedPixDTO) {

        KeyPix keyPix = keysPixRepository.getByKey(receivedPixDTO.getKey()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "key not registered at the institution Will Bank."));

        ReceivedPix receivedPix = new ReceivedPix();
        receivedPix.setKey(keyPix.getKey());
        receivedPix.setValue(receivedPixDTO.getValue());
        receivedsPixrepository.save(receivedPix);

        return receivedPixDTO;
    }
}
