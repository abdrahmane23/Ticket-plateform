package com.example.ticketPlatform.Validators;

import com.example.ticketPlatform.Repositories.QrCodeRepo;
import com.example.ticketPlatform.domain.entities.QrCode;
import com.example.ticketPlatform.exceptions.QrCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QrCodeValidator {
    private final QrCodeRepo qrCodeRepo;
    public QrCode getQrCodeIfExists(String QrValue){
        return qrCodeRepo.findByValue(QrValue)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format(
                                "QR Code with value %s was not found",QrValue
                        )
                ));
    }
}
