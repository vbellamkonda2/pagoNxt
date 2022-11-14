package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.common.PaymentResponse;
import com.example.demo.constatnts.CardType;
import com.example.demo.model.Cart;
import com.example.demo.model.Carts;
import com.example.demo.model.CartsByCustomerAndCartID;
import com.example.demo.model.Discount;
import com.example.demo.model.Product;
import com.example.demo.model.ProductIdWithQty;
import com.example.demo.model.Products;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class InternetShopController {
	
	Products products = new Products();
	Carts carts = new Carts();
	private Map<String, Double> productPrice =  new HashMap<>();
	
	@PostMapping(path ="/v1/addProduct", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody Product product) throws JsonProcessingException {
		products.getListOfProducts().add(product);
		productPrice.put(product.getProductId(), product.getPrice());
		ApiResponse apiResponse = setSuccessResponse();
		apiResponse.setDescription(product.getName() + " added successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@GetMapping(path = "/v1/getProducts", produces = "application/json")
	public ResponseEntity<List<Product>>  getAllProducts() throws JsonProcessingException {
		return new ResponseEntity<>(products.getListOfProducts(), HttpStatus.OK);
	}
	
	@PostMapping(path = "/v1/addToCart", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ApiResponse>  addToCart(@RequestBody Cart cart) throws JsonProcessingException {
		carts.getCarts().add(cart);
		ApiResponse apiResponse = setSuccessResponse();
		apiResponse.setDescription(cart.getProductId() + " added successfully to Cart");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping(path = "/v1/getCarts",  produces = "application/json")
	public ResponseEntity<List<CartsByCustomerAndCartID>> getAllCarts() throws JsonProcessingException {
		List<CartsByCustomerAndCartID> cartsList = new ArrayList<>();
		Map<Pair<String, String>, List<Cart>> cartsObjAfterGroupingby = carts.getCarts().stream()
				  .collect(Collectors.groupingBy(cart -> new ImmutablePair<>(cart.getCartId(), cart.getCustomerId())));
		for (Entry<Pair<String, String>, List<Cart>> entrySet : cartsObjAfterGroupingby.entrySet()) {
			CartsByCustomerAndCartID cartsByCustomerAndCartID = setCartByCustomerAndCartId(entrySet);
			cartsList.add(cartsByCustomerAndCartID);
		}
		return new ResponseEntity<>(cartsList, HttpStatus.OK);
	}

	private CartsByCustomerAndCartID setCartByCustomerAndCartId(Entry<Pair<String, String>, List<Cart>> entrySet) {
		CartsByCustomerAndCartID cartsByCustomerAndCartID = new CartsByCustomerAndCartID();
		cartsByCustomerAndCartID.setCartId(entrySet.getKey().getLeft());
		cartsByCustomerAndCartID.setCustomerId(entrySet.getKey().getRight());
		List<Cart> cartList = entrySet.getValue();
		List<ProductIdWithQty> list = new ArrayList<>();
		for(Cart cart : cartList) {
			ProductIdWithQty productIdWithQty = new ProductIdWithQty();
			productIdWithQty.setProductId(cart.getProductId());
			productIdWithQty.setQuantity(cart.getQuantity());
			list.add(productIdWithQty);
		}
		cartsByCustomerAndCartID.setSelectedItems(list);
		return cartsByCustomerAndCartID;
	}
	
	@PostMapping(path = "/v1/removeFromCart", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ApiResponse> removeFromCart(@RequestBody Cart cart) throws JsonProcessingException {
		//if user wants to remove multiple items
//		List<Cart> listForRemove = new ArrayList<>();
//		carts.getCarts().stream().filter(strCart -> strCart.getCartId().equals(cart.getCartId())  && strCart.getCustomerId().equals(cart.getCustomerId())  
//				&& strCart.getProductId().equals(cart.getProductId())).forEach(cartForRemove -> listForRemove.add(cartForRemove));
//		carts.getCarts().removeAll(listForRemove);
		carts.getCarts().remove(cart);
		ApiResponse apiResponse = setSuccessResponse();
		apiResponse.setDescription("Item deleted successfully from Cart");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@PostMapping(path = "/v1/calculateDiscount", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PaymentResponse>  calDiscount(@RequestBody Discount discount) throws JsonProcessingException {
		Map<Pair<String, String>, List<Cart>> cartsObjAfterGroupingby = carts.getCarts().stream()
				  .collect(Collectors.groupingBy(cart -> new ImmutablePair<>(cart.getCartId(), cart.getCustomerId())));
		double totalCost = 0.0;
		double discountAmt = 0.0;
		for (Entry<Pair<String, String>, List<Cart>> entrySet : cartsObjAfterGroupingby.entrySet()) {
			CartsByCustomerAndCartID cartsByCustomerAndCartID = setCartByCustomerAndCartId(entrySet);
			if(cartsByCustomerAndCartID.getCartId().equals(discount.getCartId()) && cartsByCustomerAndCartID.getCustomerId().equals(discount.getCustomerId())) {
				Iterator<ProductIdWithQty> itr = cartsByCustomerAndCartID.getSelectedItems().stream().iterator();
				while(itr.hasNext()) {
					ProductIdWithQty prodWithQty = itr.next();
					double itemPrice = productPrice.get(prodWithQty.getProductId());
					totalCost += itemPrice * prodWithQty.getQuantity();
				}
			}
		}
		if(CardType.GOLD.toString().equalsIgnoreCase(discount.getCardType())){
			discountAmt = totalCost*20/100;
		}else if(CardType.SILVER.toString().equalsIgnoreCase(discount.getCardType())) {
			discountAmt = totalCost*10/100;
		}else {
			discountAmt = 0;
		}
		PaymentResponse response = new PaymentResponse();
		response.setCode("200");
		response.setStatus("SUCCESS");
		response.setDiscountAmt(discountAmt);
		response.setTotalCost(totalCost);
		response.setDescription("Total cost is: "+totalCost + " Discount Price is: "+discountAmt);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private ApiResponse setSuccessResponse() {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode("200");
		apiResponse.setStatus("SUCCESS");
		return apiResponse;
	}
}
