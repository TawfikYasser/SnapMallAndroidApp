package com.snapmall.snapmall;

public class Offer {
    String offerId,offerTitle,offerImage,offerPrice,offerWebsite,offerLocation,offerCategory,offerHours,priceId,colorId;
    Offer(){}

    public Offer(String offerId, String offerTitle, String offerImage, String offerPrice, String offerWebsite, String offerLocation, String offerCategory, String offerHours, String priceId, String colorId) {
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerImage = offerImage;
        this.offerPrice = offerPrice;
        this.offerWebsite = offerWebsite;
        this.offerLocation = offerLocation;
        this.offerCategory = offerCategory;
        this.offerHours = offerHours;
        this.priceId = priceId;
        this.colorId = colorId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferWebsite() {
        return offerWebsite;
    }

    public void setOfferWebsite(String offerWebsite) {
        this.offerWebsite = offerWebsite;
    }

    public String getOfferLocation() {
        return offerLocation;
    }

    public void setOfferLocation(String offerLocation) {
        this.offerLocation = offerLocation;
    }

    public String getOfferCategory() {
        return offerCategory;
    }

    public void setOfferCategory(String offerCategory) {
        this.offerCategory = offerCategory;
    }

    public String getOfferHours() {
        return offerHours;
    }

    public void setOfferHours(String offerHours) {
        this.offerHours = offerHours;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
