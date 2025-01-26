package br.com.diogomendes.model;

import br.com.diogomendes.dto.ShopItemDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "shop_item")
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_identifier")
    private String productIdentifier;

    private Integer amount;

    private Float price;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public static ShopItem convert(ShopItemDTO shopItemDTO) {
        var shopItem = new ShopItem();
        shopItem.setPrice(shopItemDTO.getPrice());
        shopItem.setAmount(shopItemDTO.getAmount());
        shopItem.setProductIdentifier(shopItemDTO.getProductIdentifier());
        return shopItem;
    }

}
