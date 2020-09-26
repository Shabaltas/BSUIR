package signal;

public class Impulse extends HarmonicSignal {
    private double dutyValue = 0.1;

    public Impulse(int amplitude, int frequence, double phase) {
        super(amplitude, frequence, phase);
    }

    public Impulse(int amplitude, int frequence, double phase, double dutyValue) {
        super(amplitude, frequence, phase);
        this.dutyValue = dutyValue;
    }

    @Override
    public double getSignalValue(double val) {
        return ((val % (2 * Math.PI)) / (2 * Math.PI) ) <= dutyValue ? 1 : 0;
    }
}
