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
public class Discount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7991126220591929031L;
	private String customerId;
    private String cartId;
    private String cardType;
}
