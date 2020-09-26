package signal.modulation;

import signal.HarmonicSignal;
import signal.Signal;

public abstract class Modulation extends Signal {
    HarmonicSignal carrier;
    HarmonicSignal modulating;

    public Modulation(HarmonicSignal carrier, HarmonicSignal modulating) {
        this.carrier = carrier;
        this.modulating = modulating;
    }
}
