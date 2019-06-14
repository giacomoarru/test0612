package com.gmail.giacomo.app.utils;

import java.time.YearMonth;

/**
 * This class is a quick validation, NOT FULLY FUNCIONAL as it does not verify against
 * checksum. Since it is just a demo project, we'll focus on the date and on the length.
 * @author Giacomo
 *
 */
public class CreditCardValidation {

	private CreditCardValidation() {
		// util class
	}
	
	/**
	 * Quick and dirty validation based on Regex (yy/mm) + (Year/Month is not in the past)
	 * @param expiryDate
	 * @return
	 */
	public static boolean validateCardExpiryDate(String expiryDate) {
		YearMonth yearMonth = YearMonth.now();
		
		return (expiryDate.matches("[0-9]{2}/(?:0[1-9]|1[0-2])")
				&& Integer.parseInt(expiryDate.substring(0, 2)) >= Integer.parseInt(String.valueOf(yearMonth.getYear()).substring(2, 4))
				&& Integer.parseInt(expiryDate.substring(3, 5)) >= yearMonth.getMonthValue());
				
	}
	
}
