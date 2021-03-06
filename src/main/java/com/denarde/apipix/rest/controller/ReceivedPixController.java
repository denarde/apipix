package com.denarde.apipix.rest.controller;

import com.denarde.apipix.domain.entity.KeyPix;
import com.denarde.apipix.domain.entity.ReceivedPix;
import com.denarde.apipix.domain.repository.KeysPix;
import com.denarde.apipix.domain.repository.ReceivedsPix;
import com.denarde.apipix.exception.RuleBusinessException;
import com.denarde.apipix.rest.dto.ReceivedPixDTO;
import com.denarde.apipix.utils.Validation;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api("Received Pix API")
@RequestMapping("/api/receivedPix")
public class ReceivedPixController {

    @Autowired
    private ReceivedsPix receivedsPixrepository;

    @Autowired
    private KeysPix keysPixRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReceivedPixDTO save(@RequestBody @Valid ReceivedPixDTO receivedPixDTO) {

        entryValidate(receivedPixDTO);

        if(!keysPixRepository.existsByKey(receivedPixDTO.getKey())){
            throw new RuleBusinessException("key not registered at the institution Will Bank.");
        }

        ReceivedPix receivedPix = new ReceivedPix();
        receivedPix.setKey(receivedPixDTO.getKey());
        receivedPix.setValue(receivedPixDTO.getValue());
        receivedsPixrepository.save(receivedPix);

        return receivedPixDTO;
    }

    private void entryValidate(ReceivedPixDTO receivedPixDTO) {

        if (!Validation.isValidValue(receivedPixDTO.getValue())) {
            throw new RuleBusinessException("Value must be greater than 0.");
        }

    }
}
