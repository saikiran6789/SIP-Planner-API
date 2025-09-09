package com.example.sipplanner.api;

import com.example.sipplanner.service.SipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/v1")
@Validated
public class SipController {
    private static final Logger log = LoggerFactory.getLogger(SipController.class);
    private final SipService sipService;
    private final AtomicLong requests = new AtomicLong();
    private final AtomicLong totalLatencyMs = new AtomicLong();

    public SipController(SipService sipService) {
        this.sipService = sipService;
    }

    @PostMapping("/sip/calc")
    public SipResponse calculate(@Valid @RequestBody SipRequest req) {
        long start = System.currentTimeMillis();
        requests.incrementAndGet();
        log.info("Received SIP calc request: monthly={} rate={} years={}", req.getMonthlyInvestment(), req.getAnnualRatePercent(), req.getYears());
        double[] res = sipService.calculateSip(req.getMonthlyInvestment(), req.getAnnualRatePercent(), req.getYears());
        long latency = System.currentTimeMillis() - start;
        totalLatencyMs.addAndGet(latency);
        log.info("SIP calc done in {} ms", latency);
        return new SipResponse(res[0], res[1], res[2]);
    }

    @GetMapping("/health")
    public Object health() {
        return new Object() {
            public final String status = "UP";
            public final String commit = System.getenv().getOrDefault("GIT_COMMIT", "unknown");
            public final String time = Instant.now().toString();
        };
    }

    @GetMapping("/metrics")
    public Object metrics() {
        long req = requests.get();
        double avgLatency = req == 0 ? 0.0 : ((double) totalLatencyMs.get()) / req;
        return new Object() {
            public final long requestsServed = req;
            public final double avgLatencyMs = Math.round(avgLatency * 100.0) / 100.0;
        };
    }
}
