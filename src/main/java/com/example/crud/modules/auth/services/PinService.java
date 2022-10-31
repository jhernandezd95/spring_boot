package com.example.crud.modules.auth.services;

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

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < LENGTH) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public Pin createPin(PinDto pinDto) {
        pinDto.setPin(getSaltString());
        Pin pin = new Pin(pinDto);
        pinRepository.save(pin);
        return pin;
    }

    public Optional<Pin> checkPin(PinDto pinDto) {
        return pinRepository.findByPinEqualsAndTypeEquals(pinDto.getPin(), pinDto.getType());
    }
}
