package com.portea.commp.smsen.util;

import com.portea.common.config.domain.ConfigParamValueDataType;

public class DataTypeUtil {

    private static final String DATA_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATA_TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATA_TIME_FORMAT = "HH:mm:ss";

    public static boolean isValueOfDataType(ConfigParamValueDataType valueDataType, String value) {
        if (value == null) {
            return false;
        }
        switch (valueDataType) {
            case BOOLEAN:
                return value.equals(Boolean.TRUE.toString()) || value.equals(Boolean.FALSE.toString());
            case DATE:
                return DateUtil.isValidDateFormat(value, DATA_DATE_FORMAT);
            case NUMBER:
                return StringUtil.isNumber(value);
            case TEXT:
                return true;
            case TIMESTAMP:
                return DateUtil.isValidDateFormat(value, DATA_TIME_STAMP_FORMAT);
            case TIME:
                return DateUtil.isValidDateFormat(value, DATA_TIME_FORMAT);
        }
        return false;
    }
}
