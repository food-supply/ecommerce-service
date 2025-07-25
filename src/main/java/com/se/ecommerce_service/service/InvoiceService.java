package com.se.ecommerce_service.service;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.repository.InvoiceRepository;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
       
}
