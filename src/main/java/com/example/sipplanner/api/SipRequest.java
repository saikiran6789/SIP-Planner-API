package com.example.sipplanner.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SipRequest {
    @NotNull
    @Min(1)
    private Double monthlyInvestment;

    @NotNull
    private Double annualRatePercent;

    @NotNull
    @Min(1)
    private Integer years;

    public Double getMonthlyInvestment() { return monthlyInvestment; }
    public void setMonthlyInvestment(Double monthlyInvestment) { this.monthlyInvestment = monthlyInvestment; }

    public Double getAnnualRatePercent() { return annualRatePercent; }
    public void setAnnualRatePercent(Double annualRatePercent) { this.annualRatePercent = annualRatePercent; }

    public Integer getYears() { return years; }
    public void setYears(Integer years) { this.years = years; }
}
