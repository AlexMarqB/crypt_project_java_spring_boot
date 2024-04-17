package com.gm2.crypto_project.controllers;

import com.gm2.crypto_project.dtos.CoinRecordDto;
import com.gm2.crypto_project.models.CoinModel;
import com.gm2.crypto_project.repository.CoinRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CoinController {
    @Autowired
    CoinRepository coinRepository;

    @PostMapping("/coin")
    public ResponseEntity<Object> createItem(@RequestBody @Valid CoinRecordDto coinRecordDto) {
        Optional<CoinModel> existingCoin = coinRepository.findByName(coinRecordDto.name());
        if(existingCoin.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A coin with this name already exists");
        }
        CoinModel coinModel = new CoinModel();
        BeanUtils.copyProperties(coinRecordDto, coinModel);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(coinRepository.save(coinModel));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not able to create coin" + error.getMessage());
        }
    }

    @GetMapping("/coin/all")
    public ResponseEntity<List<CoinModel>> getAllCoins() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(coinRepository.findByActiveTbTrue());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/coin/{id}")
    public ResponseEntity<Object> getCoinById(@PathVariable(value = "id") UUID id) {
        try {
            Optional<CoinModel> coin = coinRepository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(coin);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not able to found coin \n" + error.getMessage());
        }
    }

    @PutMapping("/coin/up/{id}")
    public ResponseEntity<Object> updateCoin(@RequestBody @Valid CoinRecordDto coinRecordDto, @PathVariable(value = "id") UUID id) {
        Optional<CoinModel> coin = coinRepository.findById(id);
        if(coin.isEmpty() || !coin.get().isActive_tb()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not able to found coin");
        }
        try {
            CoinModel coinModel = coin.get();
            BeanUtils.copyProperties(coinRecordDto, coinModel);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(coinRepository.save(coinModel));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something wen wrong \n" + error.getMessage());
        }
    }

    @DeleteMapping("/coin/del/{id}")
    public ResponseEntity<Object> deleteCoinById(@PathVariable(value = "id") UUID id) {
        Optional<CoinModel> coin = coinRepository.findById(id);
        if(coin.isEmpty() || !coin.get().isActive_tb()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not able to found coin");
        }
        try {
            coin.get().setActive_tb(false);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not able to found coin \n" + error.getMessage());
        }
    }
}
