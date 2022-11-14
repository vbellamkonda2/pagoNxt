package com.example.demo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class ProductIdWithQty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3931378664177414413L;
	
	private String productId;
	private int quantity;
	
}
