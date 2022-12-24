package com.vintechplc.telebirr.model;

import com.google.gson.annotations.SerializedName;
public class ToPayPath {

   @SerializedName("toPayUrl")
   private String toPayUrl;

   public String getToPayUrl() {
       return toPayUrl;
   }

   public void setToPayUrl(String toPayUrl) {
       this.toPayUrl = toPayUrl;
   }
}
