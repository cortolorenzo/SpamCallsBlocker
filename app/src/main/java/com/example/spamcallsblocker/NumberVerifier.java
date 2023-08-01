package com.example.spamcallsblocker;

public class NumberVerifier {

    public static boolean isFirstCharactersEqual(String chars, String number){

        String firstThreeChars = number.substring(0, chars.length());
        return firstThreeChars.equals(chars);
    }

    public static boolean checkNumberExistsInArray(String[] array, String callingNumber) {
        for (String item : array) {

            String firstCharsCallNumber = callingNumber.substring(0, item.trim().length());
            if (item.trim().equals(firstCharsCallNumber)) {
                return true;
            }
        }
        return false;
    }

}
