package com.example.sipplanner.api;

public class SipResponse {
    private double corpus;
    private double totalInvested;
    private double gain;

    public SipResponse() {}

    public SipResponse(double corpus, double totalInvested, double gain) {
        this.corpus = corpus;
        this.totalInvested = totalInvested;
        this.gain = gain;
    }

    public double getCorpus() { return corpus; }
    public void setCorpus(double corpus) { this.corpus = corpus; }

    public double getTotalInvested() { return totalInvested; }
    public void setTotalInvested(double totalInvested) { this.totalInvested = totalInvested; }

    public double getGain() { return gain; }
    public void setGain(double gain) { this.gain = gain; }
}
