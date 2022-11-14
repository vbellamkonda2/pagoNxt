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
public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3591791467935994808L;
	private String customerId;
    private String name;
    private String email;
    private String phone;
}
