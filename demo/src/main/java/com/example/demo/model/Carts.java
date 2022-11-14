package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class Carts implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -6125403122259887585L;
	private List<Cart> carts = new ArrayList<>();

}
