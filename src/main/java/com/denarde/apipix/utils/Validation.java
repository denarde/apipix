package com.denarde.apipix.utils;

import com.denarde.apipix.domain.entity.KeyPix;
import com.denarde.apipix.domain.enums.KeyType;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public final class Validation {

    public static boolean isValidKey(KeyPix keyPix) {

        if (keyPix.getKeyType().equals(KeyType.CPF)) {
            return isValidCpf(keyPix.getKey());
        } else {
            return isValidEmail(keyPix.getKey());
        }

    }

    public static boolean isValidValue(Double value) {
        return value > 0;

    }
    
    private static boolean isValidEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private static boolean isValidCpf(String cpf) {
        return cpf.trim().length() == 11;

    }

}
