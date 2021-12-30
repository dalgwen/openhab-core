/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.core.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Some utility methods for parsing and cleaning wav files
 *
 * @author Gwendal Roulleau - Initial contribution
 *
 */
public class AudioWaveUtils {

    private static final AudioFormat DEFAULT_WAVE_AUDIO_FORMAT = new AudioFormat(AudioFormat.CONTAINER_WAVE,
            AudioFormat.CODEC_PCM_SIGNED, false, 16, 705600, 44100L, 1);

    /**
     *
     * @param InputStream an inputStream of a wav file to analyze. The InputStream must have a fmt header
     *            and support the mark/reset method
     * @return The audio format, or the default audio format if an error occured
     * @throws IOException If i/o exception occurs, or if the InputStream doesn't support the mark/reset
     */
    public static AudioFormat parseWavFormat(InputStream inputStream) throws IOException {
        try {
            inputStream.mark(200); // arbitrary amount, also used by the underlying parsing package from Sun
            javax.sound.sampled.AudioFormat format = AudioSystem.getAudioInputStream(inputStream).getFormat();
            String javaSoundencoding = format.getEncoding().toString();
            String codecPCMSignedOrUnsigned;
            if (javaSoundencoding.equals(Encoding.PCM_SIGNED.toString())) {
                codecPCMSignedOrUnsigned = AudioFormat.CODEC_PCM_SIGNED;
            } else if (javaSoundencoding.equals(Encoding.PCM_UNSIGNED.toString())) {
                codecPCMSignedOrUnsigned = AudioFormat.CODEC_PCM_UNSIGNED;
            } else if (javaSoundencoding.equals(Encoding.ULAW.toString())) {
                codecPCMSignedOrUnsigned = AudioFormat.CODEC_PCM_ULAW;
            } else if (javaSoundencoding.equals(Encoding.ALAW.toString())) {
                codecPCMSignedOrUnsigned = AudioFormat.CODEC_PCM_ALAW;
            } else {
                codecPCMSignedOrUnsigned = null;
            }
            Integer bitRate = Math.round(format.getFrameRate() * format.getFrameSize()) * format.getChannels();
            Long frequency = Float.valueOf(format.getSampleRate()).longValue();
            return new AudioFormat(AudioFormat.CONTAINER_WAVE, codecPCMSignedOrUnsigned, format.isBigEndian(),
                    format.getSampleSizeInBits(), bitRate, frequency, format.getChannels());

        } catch (UnsupportedAudioFileException e) {
            // do not throw exception and assume default format to let a chance for the sink to play the stream.
            return DEFAULT_WAVE_AUDIO_FORMAT;
        } finally {
            inputStream.reset();
        }
    }
}
