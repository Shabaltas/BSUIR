package signal;

public class SawtoothSignal extends HarmonicSignal {
    public SawtoothSignal(int amplitude, int frequence, double phase) {
        super(amplitude, frequence, phase);
    }

    @Override
    public double getSignalValue(double val) {
        return  Math.atan(Math.tan(val / 2)) * 2 / Math.PI;
    }
}
