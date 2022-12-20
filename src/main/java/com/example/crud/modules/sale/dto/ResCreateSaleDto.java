package com.example.crud.modules.sale.dto;

import com.example.crud.modules.sale.entities.Sale;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResCreateSaleDto implements Serializable {
	private final Sale sale;
	private final double amount;
}
