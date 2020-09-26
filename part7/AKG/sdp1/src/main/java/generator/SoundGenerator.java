package generator;

import signal.PolyharmonicSignal;
import signal.Signal;

import static work.AudioConstants.*;

public class SoundGenerator {
    //https://audiocoding.ru/articles/2008-05-22-wav-file-structure/

    public byte[] generateSound(Signal signal) {
        //TODO try to do 4 bytes per second
        byte[] buffer = new byte[SAMPLE_RATE * SECONDS * BYTES_PER_SOUND];
        for (int i = 0; i < SAMPLE_RATE * SECONDS; i++) {
            int k = signal.getSignal(i);
            //LITTLE-ENDIAN
            buffer[2 * i] = (byte) k;
            buffer[2 * i + 1] = (byte) (k >>> 8);
        }
        return buffer;
    }


}
