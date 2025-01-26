package br.com.diogomendes.dto;

import java.time.LocalDate;

public class ShopDTO {

    private String identifier;
    private LocalDate dateShop;
    private String status;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDate getDateShop() {
        return dateShop;
    }

    public void setDateShop(LocalDate dateShop) {
        this.dateShop = dateShop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
