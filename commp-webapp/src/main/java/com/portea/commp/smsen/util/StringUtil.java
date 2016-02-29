package com.portea.commp.smsen.util;

import java.io.BufferedReader;
import java.io.IOException;

public class StringUtil {

    /**
     * Returns a string, which is in between the beginString and the endString, or a null value
     * if beginString is not found.
     */
    public static String getSubString(String response, String beginString, String endString) {
        int index = response.indexOf(beginString);
        int initialIndex = index+beginString.length();

        if(initialIndex == -1) {
            return null;
        }

        String remaining = response.substring(initialIndex);
        int finalIndex = remaining.indexOf(endString);

        if(finalIndex == -1) {
            return remaining;
        }

        return remaining.substring(0,finalIndex);
    }

    public static boolean isNumber(String string) {
        char[] chars = string.toCharArray();

        for (char c : chars) {
            if( ! Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static String read(BufferedReader reader) throws IOException {
        StringBuilder submissionResponse = new StringBuilder();
        String response;
        while((response = reader.readLine()) != null) {
            submissionResponse.append(response);
        }
        return submissionResponse.toString();
    }
}
