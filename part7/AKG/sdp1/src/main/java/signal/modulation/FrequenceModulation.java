package signal.modulation;

import signal.HarmonicSignal;

import static work.AudioConstants.SAMPLE_RATE;

//TODO ask about algorithm
public class FrequenceModulation extends Modulation {

    public FrequenceModulation(HarmonicSignal carrier, HarmonicSignal modulating) {
        super(carrier, modulating);
    }

    private double fi = 0;
    @Override
    public int getSignal(int n) {
        if (n == 0) fi = 0.0;
        int m = modulating.getSignal(n);
        fi += 2 * Math.PI * carrier.getFrequence() * (1 + m) / SAMPLE_RATE;
        return (int) (carrier.getAmplitude() * carrier.getSignalValue(fi));
    }

    /*
     public static double[] Frequency(Signal modulationSignal,
            Signal carrierSignal, int sampleRate, int seconds)
        {
            double[] result = new double[sampleRate * seconds];
            double fi = 0;

            for (int n = 0; n < seconds * sampleRate; n++)
            {
                var time = (double) n / sampleRate;
                var lfo = modulationSignal.GetSignalVolume(time);

                fi += 2 * Math.PI * carrierSignal.Frequency * (1 + lfo) / sampleRate;

                result[n] = carrierSignal.Amplitude *(1 + lfo) * carrierSignal.GetValue(fi);
            }

            return result;
        }
     */
}
