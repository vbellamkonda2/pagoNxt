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
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3715333511535097242L;
	private String productId;
	private String name;
    private double price;
}
