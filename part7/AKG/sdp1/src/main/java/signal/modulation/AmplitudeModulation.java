package signal.modulation;

import signal.HarmonicSignal;

public class AmplitudeModulation extends Modulation {

    public AmplitudeModulation(HarmonicSignal carrier, HarmonicSignal modulating) {
        super(carrier, modulating);
    }

    @Override
    public int getSignal(int n) {
        double m =  Math.abs(carrier.getAmplitude() - modulating.getAmplitude())
                / (double)(carrier.getAmplitude() + modulating.getAmplitude());
        return (int) (carrier.getSignal(n) + (1 + m * modulating.getSignal(n)));
    }
}
