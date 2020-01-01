package pri.cyjf.englishcornor;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.widget.Toast;


public class MicRecorder extends Thread {
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private volatile boolean canPlay;

    @Override
    public void run() {
        int bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        byte[] data = new byte[bufferSize * 100];
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        if (audioRecord != null) {
            audioRecord.startRecording();
            audioRecord.read(data, 0, bufferSize * 100);
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }





        /*
        for (int i = 0; i < 10; i++)
        {
            //audioRecord.read(data, bufferSize * i, bufferSize);



            for (int j = 0; j < 440; j++) {
                int period = bufferSize / 2 / 2 / 440;
                double step = 2 * 3.14159 / period;
                for (int k = 0; k < period; k++)
                {
                    data[i * bufferSize + j * period * 2 + k] = (short)(Math.sin(k * step) * 31000);
                    //data[i * bufferSize + j * period * 2 + period + k] = (short)(Math.sin(k * step) * 31000);
                }
            }
        }
        */
        /*
        for (int i = 0; i < bufferSize * 1000 / 2; ++i)
        {
            data[i] = (short)(i % 2000 * 10 + 1000);
        }
        */


        int bufferSize1 = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize * 100, AudioTrack.MODE_STATIC);

        audioTrack.write(data, 0, bufferSize * 100);
        //audioTrack.setPlaybackRate(44100);
        audioTrack.play();
        //audioTrack.reloadStaticData();
       //audioTrack.write(data, 0, bufferSize * 100);
        //audioTrack.play();

        /*
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        while (audioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED)
        {
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        audioTrack.stop();
        audioTrack.release();;
        audioTrack = null;
    }
}
