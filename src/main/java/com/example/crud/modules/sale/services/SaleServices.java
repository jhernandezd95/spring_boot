package com.example.crud.modules.sale.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.UserService;
import com.example.crud.modules.product.dto.IncrementSaleQuantityDto;
import com.example.crud.modules.product.entities.Product;
import com.example.crud.modules.product.services.ProductService;
import com.example.crud.modules.product.services.StockService;
import com.example.crud.modules.sale.dto.CreateSaleDto;
import com.example.crud.modules.sale.dto.ResCreateSaleDto;
import com.example.crud.modules.sale.dto.SaleProductDto;
import com.example.crud.modules.sale.entities.Sale;
import com.example.crud.modules.sale.entities.SaleProduct;
import com.example.crud.modules.sale.enums.SaleStatus;
import com.example.crud.modules.sale.repositories.SaleProductRepository;
import com.example.crud.modules.sale.repositories.SaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SaleServices {

	private SaleRepository saleRepository;
	private SaleProductRepository saleProductRepository;
	private ProductService productService;
	private UserService userService;
	private StockService stockService;

	@Transactional
	public ResCreateSaleDto createSale(CreateSaleDto createSaleDto) {

		User user = userService.getById(createSaleDto.getUserId());
		Sale sale = new Sale(SaleStatus.REQUESTED, user);
		saleRepository.save(sale);
		sale.setCode();
		saleRepository.save(sale);
		double totalAmount = 0;
		for (SaleProductDto saleProduct : createSaleDto.getProducts()) {
			Product product = productService.getById(saleProduct.getProductId());

			this.stockService.incrementSaleQuantity(new IncrementSaleQuantityDto(saleProduct.getProductId(), saleProduct.getProductAmount()));

			saleProductRepository.save(new SaleProduct(product.getPrice(), saleProduct.getProductAmount(), product, sale));
			totalAmount += product.getPrice() * saleProduct.getProductAmount();
		}

		return new ResCreateSaleDto(sale, totalAmount);
	}

	public Sale getOne(Long saleId) {
		Optional<Sale> optionalSale = saleRepository.findById(saleId);

		if (optionalSale.isEmpty()) {
			throw new NotFoundException("Sale not found with id " + saleId);
		}

		return optionalSale.get();
	}

	public Sale changeStatus(SaleStatus status, Long saleId) {
		Sale sale = this.getOne(saleId);

		sale.setStatus(status);

		saleRepository.save(sale);

		return sale;
	}

	public double getAmount(Long saleId) {
		Optional<Collection<SaleProduct>> optionalSaleProducts = saleProductRepository.findBySale_Id(saleId);

		if (optionalSaleProducts.isEmpty()) {
			throw new NotFoundException("Sale not found with id " + saleId);
		}

		double amount = 0;
		for (SaleProduct saleProduct : optionalSaleProducts.get()) {
			amount += saleProduct.getProductAmount() * saleProduct.getPriceAtSale();
		}

		return amount;
	}
}
