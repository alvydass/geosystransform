package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.converter.model.LksCoordinate;
import com.geosystem.transform.converter.model.WgsCoordinate;
import com.geosystem.transform.enums.CoordinateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class ResponseFactoryTest {

    @Test
    public void testGetResponseForLksCoordinate() {
        CoordinateType destinationType = CoordinateType.LKS;
        double aVal = 123.456;
        double bVal = 789.012;

        CoordinateWrapper response = ResponseFactory.getResponse(destinationType, aVal, bVal);

        assertTrue(response instanceof LksCoordinate);
        LksCoordinate lksCoordinate = (LksCoordinate) response;
        assertEquals("123", lksCoordinate.getAval());
        assertEquals("789", lksCoordinate.getBval());
    }

    @Test
    public void testGetResponseForWgsCoordinate() {
        CoordinateType destinationType = CoordinateType.WGS;
        String aVal = "40.7128";
        String bVal = "-74.006";

        CoordinateWrapper response = ResponseFactory.getResponse(destinationType, Double.parseDouble(aVal), Double.parseDouble(bVal));

        assertTrue(response instanceof WgsCoordinate);
        WgsCoordinate wgsCoordinate = (WgsCoordinate) response;
        assertEquals(aVal, wgsCoordinate.getAval());
        assertEquals(bVal, wgsCoordinate.getBval());
    }
}
