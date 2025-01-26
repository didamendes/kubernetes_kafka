package br.com.diogomendes.controller;

import br.com.diogomendes.dto.ShopReportDTO;
import br.com.diogomendes.repository.ReportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop_report")
public class ShopController {

    private final ReportRepository repository;

    public ShopController(ReportRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ShopReportDTO> getShopReport() {
        return repository.findAll().stream().map(ShopReportDTO::convert).collect(Collectors.toList());
    }

}
