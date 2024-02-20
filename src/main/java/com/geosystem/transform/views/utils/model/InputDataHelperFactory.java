package com.geosystem.transform.views.utils.model;

import com.geosystem.transform.enums.CoordinateType;

public class InputDataHelperFactory {

    public static InputDataHelper getHelper(String cordSystem) {
        CoordinateType mimeType = CoordinateType.valueOf(cordSystem);
        switch (mimeType) {
            case WGS: return new WgsInputDataHelper();
            case LKS: return new LksInputDataHelper();
            default:
                throw new IllegalArgumentException("Unsupported file type for file: " + cordSystem);
        }
    }
}
