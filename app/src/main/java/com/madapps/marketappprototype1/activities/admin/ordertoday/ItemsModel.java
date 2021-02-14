package com.madapps.marketappprototype1.activities.admin.ordertoday;

import com.google.firebase.firestore.DocumentReference;

public class ItemsModel {

    Long srno;
    String quantity;
    String ItCode;


    public ItemsModel() {

    }

    public ItemsModel(Long srno, String quantity, String ItCode) {
        this.srno = srno;
        this.ItCode = ItCode;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getItCode() {
        return ItCode;
    }

    public Long getSrno() {
        return srno;
    }

  //  public void setSrno(String srno) {
 //      this.srno = srno;
  //  }
}
