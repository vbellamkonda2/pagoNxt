package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Data
@ToString
public class Products implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7885041561911505337L;
	private final List<Product> listOfProducts = new ArrayList<>();
}
