package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import com.example.demo.common.ApiResponse;
import com.example.demo.common.PaymentResponse;
import com.example.demo.constatnts.CardType;
import com.example.demo.controller.InternetShopController;
import com.example.demo.model.Cart;
import com.example.demo.model.CartsByCustomerAndCartID;
import com.example.demo.model.Discount;
import com.example.demo.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ControllerTest extends DemoApplicationTests{
	@InjectMocks
	private InternetShopController internetShopController;
	
	@Test
	public void testAddProduct() throws JsonProcessingException {
		Product prod = new Product();
		prod.setName("Laptop");
		prod.setPrice(999);
		prod.setProductId("P00001");
		ResponseEntity<ApiResponse> response = internetShopController.addProduct(prod);
		Assertions.assertEquals("Laptop added successfully", response.getBody().getDescription());
	}
	
	@Test
	public void testGetAllProducts() throws JsonProcessingException {
		Product prod = new Product();
		prod.setName("Laptop");
		prod.setPrice(999);
		prod.setProductId("P00001");
		internetShopController.addProduct(prod);
		Product prod2 = new Product();
		prod2.setName("Book");
		prod2.setPrice(99);
		prod2.setProductId("P00002");
		internetShopController.addProduct(prod2);
		ResponseEntity<List<Product>> response = internetShopController.getAllProducts();
		Assertions.assertEquals(2, response.getBody().size());
	}
	
	@Test
	public void testAddToCart() throws JsonProcessingException {
		Cart cart = new Cart();
		cart.setCartId("CART00001");
		cart.setCustomerId("CUST00001");
		cart.setProductId("P00001");
		ResponseEntity<ApiResponse> response = internetShopController.addToCart(cart);
		Assertions.assertEquals("P00001 added successfully to Cart", response.getBody().getDescription());
	}
	
	/**
	 * Here we are considering single cart based on CartID and CustomerID
	 * adding single item to each cart
	 * @throws JsonProcessingException
	 */
	@Test
	public void testGetAllCarts() throws JsonProcessingException {
		Cart cart = new Cart();
		cart.setCartId("CART00001");
		cart.setCustomerId("CUST00001");
		cart.setProductId("P00001");
		internetShopController.addToCart(cart);
		Cart cart2 = new Cart();
		cart2.setCartId("CART00002");
		cart2.setCustomerId("CUST00002");
		cart2.setProductId("P00002");
		internetShopController.addToCart(cart2);
		ResponseEntity<List<CartsByCustomerAndCartID>> response = internetShopController.getAllCarts();
		Assertions.assertEquals(2, response.getBody().size());
	}
	
	
	/**
	 * Here we are considering single cart based on CartID and CustomerID
	 * adding multiple items to single cart and one item to second cart, total 2 carts.
	 * @throws JsonProcessingException
	 */
	@Test
	public void testGetAllCartsWithMultipleItems() throws JsonProcessingException {
		Cart cart = new Cart();
		cart.setCartId("CART00001");
		cart.setCustomerId("CUST00001");
		cart.setProductId("P00001");
		internetShopController.addToCart(cart);
		Cart cart2 = new Cart();
		cart2.setCartId("CART00001");
		cart2.setCustomerId("CUST00001");
		cart2.setProductId("P00002");
		internetShopController.addToCart(cart2);
		Cart cart3 = new Cart();
		cart3.setCartId("CART00002");
		cart3.setCustomerId("CUST00002");
		cart3.setProductId("P00002");
		internetShopController.addToCart(cart3);
		ResponseEntity<List<CartsByCustomerAndCartID>> response = internetShopController.getAllCarts();
		Assertions.assertEquals(2, response.getBody().size());
	}
	
	/**
	 * Here we are considering single cart based on CartID and CustomerID
	 * adding multiple items to single cart and one item to second cart, total 2 carts.
	 * removing item from 2nd cart
	 * @throws JsonProcessingException
	 */
	@Test
	public void testRemoveFromCart() throws JsonProcessingException {
		Cart cart = new Cart();
		cart.setCartId("CART00001");
		cart.setCustomerId("CUST00001");
		cart.setProductId("P00001");
		internetShopController.addToCart(cart);
		Cart cart2 = new Cart();
		cart2.setCartId("CART00001");
		cart2.setCustomerId("CUST00001");
		cart2.setProductId("P00002");
		internetShopController.addToCart(cart2);
		Cart cart3 = new Cart();
		cart3.setCartId("CART00002");
		cart3.setCustomerId("CUST00002");
		cart3.setProductId("P00002");
		internetShopController.addToCart(cart3);
		ResponseEntity<List<CartsByCustomerAndCartID>> response = internetShopController.getAllCarts();
		Assertions.assertEquals(2, response.getBody().size());
		
		//Deleting item from Cart1(CART00001), it has multiple (2) items
		ResponseEntity<ApiResponse> delResponse = internetShopController.removeFromCart(cart);
		Assertions.assertEquals("Item deleted successfully from Cart", delResponse.getBody().getDescription());
		
		ResponseEntity<List<CartsByCustomerAndCartID>> responseAfterDelete = internetShopController.getAllCarts();
		Assertions.assertEquals(2, responseAfterDelete.getBody().size());
		
		//Deleting item from Cart1(CART00002), it has single item
		delResponse = internetShopController.removeFromCart(cart3);
		Assertions.assertEquals("Item deleted successfully from Cart", delResponse.getBody().getDescription());
		
		responseAfterDelete = internetShopController.getAllCarts();
		Assertions.assertEquals(1, responseAfterDelete.getBody().size());
	}
	
	
	@Test
	public void testDiscount() throws JsonProcessingException {
		Product prod = new Product();
		prod.setName("Laptop");
		prod.setPrice(999);
		prod.setProductId("P00001");
		internetShopController.addProduct(prod);
		Product prod2 = new Product();
		prod2.setName("Book");
		prod2.setPrice(99);
		prod2.setProductId("P00002");
		internetShopController.addProduct(prod2);
		Product prod3 = new Product();
		prod3.setName("Bottle");
		prod3.setPrice(5);
		prod3.setProductId("P00003");
		internetShopController.addProduct(prod3);
		
		Cart cart = new Cart();
		cart.setCartId("CART00001");
		cart.setCustomerId("CUST00001");
		cart.setProductId("P00001");
		cart.setQuantity(1);
		internetShopController.addToCart(cart);
		Cart cart2 = new Cart();
		cart2.setCartId("CART00001");
		cart2.setCustomerId("CUST00001");
		cart2.setProductId("P00003");
		cart2.setQuantity(2);
		internetShopController.addToCart(cart2);
		Cart cart3 = new Cart();
		cart3.setCartId("CART00002");
		cart3.setCustomerId("CUST00002");
		cart3.setProductId("P00002");
		cart3.setQuantity(2);
		internetShopController.addToCart(cart3);
		internetShopController.getAllCarts();
		Discount discount = new Discount();
		discount.setCardType(CardType.GOLD.name());
		discount.setCartId("CART00001");
		discount.setCustomerId("CUST00001");
		ResponseEntity<PaymentResponse> response = internetShopController.calDiscount(discount);
		Assertions.assertEquals(201.8, response.getBody().getDiscountAmt());
		discount.setCardType(CardType.SILVER.name());
		response = internetShopController.calDiscount(discount);
		Assertions.assertEquals(100.9, response.getBody().getDiscountAmt());
		discount.setCardType(CardType.NORMAL.name());
		response = internetShopController.calDiscount(discount);
		Assertions.assertEquals(0.0, response.getBody().getDiscountAmt());
		
		
		discount = new Discount();
		discount.setCardType(CardType.GOLD.name());
		discount.setCartId("CART00002");
		discount.setCustomerId("CUST00002");
		response = internetShopController.calDiscount(discount);
		Assertions.assertEquals(39.6, response.getBody().getDiscountAmt());
	}
}
