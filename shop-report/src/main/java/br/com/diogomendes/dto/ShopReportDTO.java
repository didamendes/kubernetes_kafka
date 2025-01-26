package br.com.diogomendes.dto;

import br.com.diogomendes.model.ShopReport;

public class ShopReportDTO {

    private String identifier;

    private Integer amount;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public static ShopReportDTO convert(ShopReport shopReport) {
        var shopDTO = new ShopReportDTO();
        shopDTO.setAmount(shopReport.getAmount());
        shopDTO.setIdentifier(shopReport.getIdentifier());
        return shopDTO;
    }

}
