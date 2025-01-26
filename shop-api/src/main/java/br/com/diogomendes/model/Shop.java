package br.com.diogomendes.model;

import br.com.diogomendes.dto.ShopDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private String status;

    @Column(name = "date_shop")
    private LocalDate dateShop;

    @Column(name = "buyer_identifier")
    private String buyerIdentifier;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shop")
    private List<ShopItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public LocalDate getDateShop() {
        return dateShop;
    }

    public void setDateShop(LocalDate dateShop) {
        this.dateShop = dateShop;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public void setItems(List<ShopItem> items) {
        this.items = items;
    }

    public static Shop convert(ShopDTO shopDTO) {
        var shop = new Shop();
        shop.setIdentifier(shopDTO.getIdentifier());
        shop.setStatus(shopDTO.getStatus());
        shop.setDateShop(shopDTO.getDateShop());
        shop.setBuyerIdentifier(shopDTO.getBuyerIdentifier());
        shop.setItems(shopDTO.getItems().stream().map(ShopItem::convert).collect(Collectors.toList()));
        return shop;
    }

}
