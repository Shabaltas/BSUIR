package signal;

import static work.AudioConstants.SAMPLE_RATE;

public abstract class HarmonicSignal extends Signal {

    private int amplitude;
    private int frequence;
    private double phase;

    public HarmonicSignal(int amplitude, int frequence, double phase) {
        super();
        this.amplitude = amplitude;
        this.frequence = frequence;
        this.phase = phase;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public int getFrequence() {
        return frequence;
    }

    public double getPhase() {
        return phase;
    }

    public int getSignal(int n) {
        return (int) (amplitude * getSignalValue(getValue(n)));
    };

    public abstract double getSignalValue(double val) ;

    private double getValue(int n) {
        return 2 * Math.PI * frequence * n / SAMPLE_RATE + phase;
    }
}
