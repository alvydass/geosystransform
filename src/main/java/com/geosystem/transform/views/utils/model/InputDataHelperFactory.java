package com.geosystem.transform.views.utils.model;

import com.geosystem.transform.enums.CoordinateType;

public class InputDataHelperFactory {

    public static InputDataHelper getHelper(String cordSystem) {
        CoordinateType mimeType = CoordinateType.valueOf(cordSystem);
        return switch (mimeType) {
            case WGS -> new WgsInputDataHelper();
            case LKS -> new LksInputDataHelper();
            case NAD -> new NadInputDataHelper();
            case ETRS -> new EtrsCoordinateHelper();
            case SIRGAS -> new SirgasCoordinateHelper();
            case GDA -> new GdaCoordinateHelper();
            case JGD -> new JgdCoordinateHelper();
            case PZ90 -> new Pz90CoordinateHelper();
            case CGCS2019 -> new CgcsCoordinateHelper();
            case HARTEBEESTHOEK -> new HartebeetCoordinateHelper();
            default -> throw new IllegalArgumentException("Unsupported file type for file: " + cordSystem);
        };
    }
}
