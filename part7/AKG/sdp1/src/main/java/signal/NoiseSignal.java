package signal;

import java.util.Random;

public class NoiseSignal extends HarmonicSignal {
    private Random random = new Random();
    public NoiseSignal(int amplitude, int frequence, double phase) {
        super(amplitude, frequence, phase);
    }

    //TODO
    @Override
    public double getSignalValue(double val) {
        return random.nextDouble() ;
    }
}
