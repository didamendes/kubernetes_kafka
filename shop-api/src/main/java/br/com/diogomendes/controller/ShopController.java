package br.com.diogomendes.controller;

import br.com.diogomendes.dto.ShopDTO;
import br.com.diogomendes.events.KafkaClient;
import br.com.diogomendes.model.Shop;
import br.com.diogomendes.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopRepository repository;
    private final KafkaClient kafkaClient;

    public ShopController(ShopRepository repository, KafkaClient kafkaClient) {
        this.repository = repository;
        this.kafkaClient = kafkaClient;
    }

    @GetMapping
    public List<ShopDTO> listar() {
        return repository.findAll().stream().map(ShopDTO::convert).collect(Collectors.toList());
    }

    @PostMapping
    public ShopDTO salvar(@RequestBody ShopDTO shopDTO) {
        shopDTO.setIdentifier(UUID.randomUUID().toString());
        shopDTO.setDateShop(LocalDate.now());
        shopDTO.setStatus("PENDING");

        var shop = Shop.convert(shopDTO);
        for (var shopItem : shop.getItems()) {
            shopItem.setShop(shop);
        }

        shopDTO = ShopDTO.convert(repository.save(shop));
        kafkaClient.sendMessage(shopDTO);
        return shopDTO;
    }

}
