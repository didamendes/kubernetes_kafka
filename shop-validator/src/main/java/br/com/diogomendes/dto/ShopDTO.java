package br.com.diogomendes.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShopDTO {

    private String identifier;
    private LocalDate dateShop;
    private String status;
    private String buyerIdentifier;
    private List<ShopItemDTO> items = new ArrayList<>();

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

    public String getBuyerIdentifier() {
        return buyerIdentifier;
    }

    public void setBuyerIdentifier(String buyerIdentifier) {
        this.buyerIdentifier = buyerIdentifier;
    }

    public List<ShopItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ShopItemDTO> items) {
        this.items = items;
    }
}
