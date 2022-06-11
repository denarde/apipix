package com.denarde.apipix.rest.controller;

import com.denarde.apipix.domain.entity.KeyPix;
import com.denarde.apipix.domain.repository.KeysPix;
import com.denarde.apipix.exception.RuleBusinessException;
import com.denarde.apipix.utils.Validation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/keypix")
public class KeyPixController {

    @Autowired
    private KeysPix repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KeyPix save(@RequestBody @Valid KeyPix keyPix) {

        entryValidate(keyPix);

        return repository.save(keyPix);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody @Valid KeyPix keyPix) {

        entryValidate(keyPix);

        repository
                .findById(id)
                .map(p -> {
                    keyPix.setId(p.getId());
                    repository.save(keyPix);
                    return keyPix;
                }).orElseThrow(() ->
                new EntityNotFoundException("keyPix Not Found."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repository
                .findById(id)
                .map(p -> {
                    repository.delete(p);
                    return Void.TYPE;
                }).orElseThrow(() ->
                new EntityNotFoundException("keyPix Not Found."));
    }

    @GetMapping("{id}")
    public KeyPix getById(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("keyPix Not Found."));
    }

    @GetMapping
    public List<KeyPix> getAll() {
        return repository.findAll();

    }

    private void entryValidate(KeyPix keyPix) {

        if (!Validation.isValidKey(keyPix)) {
            throw new RuleBusinessException("Invalid Key.");
        }

        if (repository.existsByKey(keyPix.getKey())) {
            throw new RuleBusinessException("Key already exists.");
        }


    }

}
