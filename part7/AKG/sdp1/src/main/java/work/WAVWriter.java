package work;

import generator.SoundGenerator;
import signal.*;
import signal.modulation.AmplitudeModulation;
import signal.modulation.FrequenceModulation;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import static work.AudioConstants.*;

public class WAVWriter {

    public void writeToWAVFile(String filename, byte[] buffer) throws IOException {

        File out = new File(filename);
        AudioFormat format = new AudioFormat((float)SAMPLE_RATE, BITS, CHANNELS, IS_SIGNED, IS_BIG_ENDIAN);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
    }
    public static void main(String[] args) throws IOException {
        HarmonicSignal s = new SinSignal(32000, 440, 0);
        Signal ms = new FrequenceModulation(s, new SinSignal(4000, 100, 0 ));
        byte[] b = new SoundGenerator().generateSound(s);
        new WAVWriter().writeToWAVFile("NOTmodFr.wav", b);
    }
}
