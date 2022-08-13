package com.clickpay.utils;

public class StringUtil {

    public static String[] extractFirstNameAndLastNameFromNameField(String name) {
        String firstName = null;
        String lastName = null;
        if (name.trim().contains(" ")) {
            String nameData[] = name.trim().split(" ");
            if (nameData.length > 2) {
                for (int i = 0; i < nameData.length-1; i++) {
                    firstName = ""+ nameData[i];
                }
            } else if (nameData.length == 2) {
                firstName = nameData[0];
                lastName = nameData[1];
            }else{
                firstName = name;
            }
        }
        return new String[]{firstName, lastName};
    }
}
