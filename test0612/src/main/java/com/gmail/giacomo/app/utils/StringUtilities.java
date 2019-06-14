package com.gmail.giacomo.app.utils;

import java.util.Random;

public final class StringUtilities {

	private static Random random = new Random();
	
	private StringUtilities() {
		throw new UnsupportedOperationException("Cannot instantiate an Utility Class.");
	}
	
	/**
	 * Generates a random credit card number
	 * @return the generated credit card number
	 */
	// 
	public static String getRandomCreditCardNumber() {

		StringBuilder builder = new StringBuilder();
		
		// credit card number length=16
		for (int i=1; i<16; i++) {
			builder.append(random.nextInt(9));	
		}
		
		return builder.toString();
		
	}
}
