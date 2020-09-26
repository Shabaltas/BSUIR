package signal;

//https://habr.com/ru/post/126835/
public class TriangleSignal extends HarmonicSignal {
    public TriangleSignal(int amplitude, int frequence, double phase) {
        super(amplitude, frequence, phase);
    }

    @Override
    public double getSignalValue(double val) {
        return  (Math.asin(Math.sin(val)) * 2 / Math.PI);
    }
}
