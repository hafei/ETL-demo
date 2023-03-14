package com.hafei.etl.model;

public class CusipInfo {
    private String cusip;

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public double getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(double couponRate) {
        this.couponRate = couponRate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getParValue() {
        return parValue;
    }

    public void setParValue(double parValue) {
        this.parValue = parValue;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    private String issuer;
    private String issueDate;
    private String maturityDate;
    private double couponRate;
    private double price;
    private double parValue;
    private double marketValue;
    private String rating;
    private String sector;

    public CusipInfo(String cusip, String issuer, String issueDate, String maturityDate, double couponRate,
                     double price, double parValue, double marketValue, String rating, String sector) {
        super();
        this.cusip = cusip;
        this.issuer = issuer;
        this.issueDate = issueDate;
        this.maturityDate = maturityDate;
        this.couponRate = couponRate;
        this.price = price;
        this.parValue = parValue;
        this.marketValue = marketValue;
        this.rating = rating;
        this.sector = sector;
    }

    // Getters and setters
}