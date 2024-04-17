package com.gm2.crypto_project.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record CoinRecordDto(String name, BigDecimal price, BigDecimal quantity) {
}
