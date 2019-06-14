package com.gmail.giacomo.app.security;

import java.util.Arrays;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;



public final class StrongPasswordValidator {

	private StrongPasswordValidator() {
		// util class
	}
	
	private static PasswordValidator validator = new PasswordValidator(Arrays.asList(
			// length between 8 and 128 characters
			  new LengthRule(8, 128),

			  // at least one upper-case character
			  new CharacterRule(EnglishCharacterData.UpperCase, 1),

			  // at least one lower-case character
			  new CharacterRule(EnglishCharacterData.LowerCase, 1),

			  // at least one digit character
			  new CharacterRule(EnglishCharacterData.Digit, 1),

			  // at least one symbol (special character)
			  new CharacterRule(EnglishCharacterData.Special, 1),

			  // no whitespace
			  new WhitespaceRule())); 
	
	
	public static RuleResult validate(String password) {
		return validator.validate(new PasswordData(new String(password)));
	}
	
}
