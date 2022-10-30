package com.example.crud.modules.auth.services;

import com.example.crud.modules.auth.dto.ActiveUserDto;
import com.example.crud.modules.auth.dto.PinDto;
import com.example.crud.modules.auth.entities.Pin;
import com.example.crud.modules.auth.repositories.PinRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class PinService {

    private PinRepository pinRepository;

    private static final int LENGTH = 5;

    private String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public Pin createPin(PinDto pinDto) {
        pinDto.setPin(getSaltString(LENGTH));
        Pin pin = new Pin(pinDto);
        pinRepository.save(pin);
        return pin;
    }

    public Optional<Pin> checkPin(PinDto pinDto) {
        Optional<Pin> pin = pinRepository.findByPinEqualsAndTypeEquals(pinDto.getPin(), pinDto.getType());
        return pin;
    }
}
