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
public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 125804807943516722L;
	private String cartId;
	private String productId;
	private String customerId;
	private int quantity;
}
