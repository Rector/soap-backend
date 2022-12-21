package ru.kir.soap.backend.utils;

public class Validator {
    public static boolean checkValidString(String str) {

        if (str == null || str.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean checkValidPassword(String password) {
        boolean checkPassword = false;
        char[] arrayChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder stringBuilder = new StringBuilder();

        boolean checkNumbers = false;
        for (int i = 0; i < password.length(); i++) {
            for (int j = 0; j < arrayChar.length; j++) {
                if (password.charAt(i) == arrayChar[j]) {
                    checkPassword = true;
                    checkNumbers = true;
                    break;
                }
            }

            if (!checkNumbers) {
                stringBuilder.append(password.charAt(i));
            }
            checkNumbers = false;
        }

        if (!checkPassword) {
            return false;
        }

        checkPassword = false;
        String strNoNumbers = stringBuilder.toString();
        String upperCaseStr = strNoNumbers.toUpperCase();

        for (int i = 0; i < strNoNumbers.length(); i++) {
            if (strNoNumbers.charAt(i) == upperCaseStr.charAt(i)) {
                checkPassword = true;
                break;
            }
        }

        return checkPassword;
    }

    private Validator() {
    }

}
