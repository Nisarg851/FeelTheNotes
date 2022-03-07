package com.example.feelthenote.Network;

public class PromoCodeRequest {

    String Promo_Code, Package_ID;

    public PromoCodeRequest(String Promo_Code,String Package_ID){
        this.Promo_Code = Promo_Code;
        this.Package_ID = Package_ID;
    }

    public String getPromo_Code() {
        return Promo_Code;
    }

    public void setPromo_Code(String promo_Code) {
        Promo_Code = promo_Code;
    }

    public String getPackage_ID() {
        return Package_ID;
    }

    public void setPackage_ID(String package_ID) {
        Package_ID = package_ID;
    }
}
