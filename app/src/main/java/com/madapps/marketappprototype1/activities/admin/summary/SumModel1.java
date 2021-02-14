package com.madapps.marketappprototype1.activities.admin.summary;

public class SumModel1 {

    String ItCode;
    String ItName;
    String ItType;

    public SumModel1() {
    }

    public SumModel1(String itCode, String itName, String itType) {
        ItCode = itCode;
        ItName = itName;
        ItType = itType;
    }

    public String getItCode() {
        return ItCode;
    }

    public String getItName() {
        return ItName;
    }

    public String getItType() {
        return ItType;
    }
}
