package com.mycom.myapp.entity.key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor                                                      
public class CodeKey implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String groupCode;
	private String code;

}
