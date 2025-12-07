package com.example.ticketPlatform.services;


import com.example.ticketPlatform.domain.entities.QrCode;
import com.example.ticketPlatform.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

  QrCode generateQrCode(Ticket ticket);

  byte[] getQrCodeImageForUserAndTicket(UUID uuid, UUID ticketId);
}
