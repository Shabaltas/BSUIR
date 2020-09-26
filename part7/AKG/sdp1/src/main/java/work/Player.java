package work;

import generator.SoundGenerator;
import signal.SawtoothSignal;
import signal.SinSignal;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static work.AudioConstants.*;

public class Player {

    public void play(byte[] buffer) throws LineUnavailableException {
        InputStream byteArrayInputStream
                = new ByteArrayInputStream(buffer);
        AudioFormat audioFormat = getAudioFormat();
        AudioInputStream audioInputStream = new AudioInputStream(
                        byteArrayInputStream, audioFormat,
                        buffer.length/audioFormat.getFrameSize());
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
        try{
            byte[] tempBuffer = new byte[10000];
            int cnt;
            while((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1){
                if(cnt > 0){
                    //Пишем данные во внутренний
                    // буфер канала
                    // откуда оно передастся
                    // на звуковой выход
                    sourceDataLine.write(tempBuffer, 0, cnt);
                }
            }
            sourceDataLine.drain();
            sourceDataLine.close();
        }catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private AudioFormat getAudioFormat(){
        return new AudioFormat(
                SAMPLE_RATE,
                BITS,
                CHANNELS,
                IS_SIGNED,
                IS_BIG_ENDIAN);
    }
    public static void main(String[] args) throws LineUnavailableException {
        byte[] b = new SoundGenerator().generateSound(new SawtoothSignal(32000, 440, 0));
        new Player().play(b);
    }
}
