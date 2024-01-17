package com.jt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayPair<U,V> {
	private U optionValue;
	private V optionDisplayString;
}
