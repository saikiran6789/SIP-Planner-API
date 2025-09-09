package com.example.sipplanner.service;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SipService {

    /**
     * Calculates the future value (corpus) of a monthly SIP using standard formula:
     * FV = P * [ ( (1 + r)^n - 1 ) / r ] * (1 + r)
     * where r = monthly rate, n = months, P = monthly investment.
     *
     * Returns array: {corpus, totalInvested, gain}
     */
    public double[] calculateSip(double monthlyInvestment, double annualRatePercent, int years) {
        Objects.requireNonNull(monthlyInvestment);
        Objects.requireNonNull(annualRatePercent);
        if (monthlyInvestment <= 0) throw new IllegalArgumentException("monthlyInvestment must be > 0");
        if (years <= 0) throw new IllegalArgumentException("years must be > 0");

        double r = annualRatePercent / 100.0 / 12.0;
        int n = years * 12;
        double corpus;
        if (r == 0.0) {
            corpus = monthlyInvestment * n;
        } else {
            double factor = Math.pow(1 + r, n);
            corpus = monthlyInvestment * ( (factor - 1) / r ) * (1 + r);
        }
        double totalInvested = monthlyInvestment * n;
        double gain = corpus - totalInvested;
        return new double[] { round(corpus), round(totalInvested), round(gain) };
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    /**
     * Simple portfolio health check:
     * - if equityPercent >= 70 -> Aggressive
     * - 40-69 -> Balanced
     * - <40 -> Conservative
     */
    public String portfolioHealth(int equityPercent, int debtPercent) {
        if (equityPercent < 0 || debtPercent < 0) throw new IllegalArgumentException("percents must be >=0");
        int total = equityPercent + debtPercent;
        if (total == 0) return "No allocation";
        int eq = equityPercent * 100 / total;
        if (eq >= 70) return "Aggressive";
        if (eq >= 40) return "Balanced";
        return "Conservative";
    }
}
