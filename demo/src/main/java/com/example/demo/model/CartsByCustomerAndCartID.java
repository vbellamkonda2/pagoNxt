package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CartsByCustomerAndCartID implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7646335318155743440L;
	private String cartId;
	private String customerId; 
	private List<ProductIdWithQty> selectedItems = new ArrayList<>();

}
