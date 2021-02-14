package com.madapps.marketappprototype1.activities.admin;

import com.google.firebase.firestore.DocumentReference;

public class ItemModel {
    String srno;
    String quantity;
    DocumentReference ItCode;


    public ItemModel() {

    }

    public ItemModel(String srno,String quantity,DocumentReference ItCode) {
        this.srno = srno;
        this.ItCode = ItCode;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public DocumentReference getItCode() {
        return ItCode;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

}
