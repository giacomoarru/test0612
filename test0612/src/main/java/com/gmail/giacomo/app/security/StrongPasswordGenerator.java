package com.gmail.giacomo.app.security;

import java.util.Arrays;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.passay.CharacterData;

public final class StrongPasswordGenerator {

	private StrongPasswordGenerator() {
		// util class
	}
	
	/**
	 * Leveraging Passay Libraries to generate compliant passwords
	 * @param length
	 * @return generated password
	 */
	public static String generatePassword(int length) {
		
		List<CharacterRule> rules = Arrays.asList(
				  // at least one upper-case character
				  new CharacterRule(EnglishCharacterData.UpperCase, 1),

				  // at least one lower-case character
				  new CharacterRule(EnglishCharacterData.LowerCase, 1),

				  // at least one digit character
				  new CharacterRule(EnglishCharacterData.Digit, 1),

				  // at least one symbol (special character)
				  new CharacterRule(new CharacterData() {
					  @Override
					  public String getErrorCode() {
					    return "ERR_SPACE";
					  }

					  @Override
					  public String getCharacters() {
					    return "-_!.?=*+";
					  }
					}, 1));
				  

				PasswordGenerator generator = new PasswordGenerator();

				// Generated password is X characters long, which complies with policy
				return generator.generatePassword(length, rules);
	}
	
	
}
