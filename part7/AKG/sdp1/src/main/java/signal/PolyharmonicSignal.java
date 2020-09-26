package signal;

import java.util.Arrays;

public class PolyharmonicSignal extends Signal {
    private Signal[] signals;
    public PolyharmonicSignal(Signal ...signals) {
        super();
        this.signals = signals;
    }

    @Override
    public int getSignal(int n) {
        int[] sum = new int[1];
        Arrays.stream(signals).forEach(signal -> sum[0] += signal.getSignal(n));
        return sum[0];
    }
}
