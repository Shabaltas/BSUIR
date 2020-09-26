package signal;


public class SinSignal extends HarmonicSignal{

    public SinSignal(int amplitude, int frequence, double phase) {
        super(amplitude, frequence, phase);
    }

    @Override
    public double getSignalValue(double val) {
        return Math.sin(val);
    }
}
