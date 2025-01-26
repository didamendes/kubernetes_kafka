package br.com.diogomendes.dto;

import br.com.diogomendes.model.ShopItem;
import lombok.Getter;
import lombok.Setter;

public class ShopItemDTO {

    private String productIdentifier;
    private Integer amount;
    private Float price;

    public String getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(String productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public static ShopItemDTO convert(ShopItem shopItem) {
        var shopItemDTO = new ShopItemDTO();
        shopItemDTO.setPrice(shopItem.getPrice());
        shopItemDTO.setAmount(shopItem.getAmount());
        shopItemDTO.setProductIdentifier(shopItem.getProductIdentifier());
        return shopItemDTO;
    }

}
