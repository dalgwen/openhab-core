/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
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
package org.openhab.core.voice;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.audio.AudioException;
import org.openhab.core.audio.AudioFormat;
import org.openhab.core.audio.AudioStream;

/**
 * This is the interface that a keyword spotting service has to implement.
 *
 * @author Kelly Davis - Initial contribution
 * @author Kai Kreuzer - Refactored to use AudioStream
 */
@NonNullByDefault
public abstract class KSService implements DialogTriggerService {

    /**
     * Obtain the Locales available from this KSService
     *
     * @return The Locales available from this service
     */
    public abstract Set<Locale> getSupportedLocales();

    /**
     * Obtain the audio formats supported by this KSService
     *
     * @return The audio formats supported by this service
     */
    public abstract Set<AudioFormat> getSupportedFormats();

    /**
     * This method starts the process of keyword spotting
     *
     * The audio data of the passed {@link AudioStream} is passed to the keyword
     * spotting engine. The keyword spotting attempts to spot {@code keyword} as
     * being spoken in the passed {@code Locale}. Spotted keyword is indicated by
     * fired {@link DialogTriggerEvent} events targeting the passed {@link DialogTriggerListener}.
     *
     * The passed {@link AudioStream} must be of a supported {@link AudioFormat}.
     * In other words an {@link AudioFormat} compatible with one returned from
     * the {@code getSupportedFormats()} method.
     *
     * The passed {@code Locale} must be supported. That is to say it must be
     * a {@code Locale} returned from the {@code getSupportedLocales()} method.
     *
     * The passed {@code keyword} is the keyword which should be spotted.
     *
     * The method is supposed to return fast, i.e. it should only start the spotting as a background process.
     *
     * @param dialogTriggerListener Non-null {@link DialogTriggerListener} that {@link DialogTriggerEvent} events target
     * @param audioStream The {@link AudioStream} from which keywords are spotted
     * @param locale The {@code Locale} in which the target keywords are spoken
     * @param keyword The keyword which to spot
     * @return A {@link DialogTriggerServiceHandle} used to abort keyword spotting
     * @throws DialogTriggerException if any parameter is invalid or a problem occurs
     */
    public abstract DialogTriggerServiceHandle spot(DialogTriggerListener dialogTriggerListener, AudioStream audioStream, Locale locale, String keyword)
            throws DialogTriggerException;


    /**
     * The default method is available for legacy keyword spotting services.
     * @param dialogTriggerListener the listener to trigger when the dialog should start
     * @param dialogContext The {@link DialogContext}  the dialog context
     * @return A {@link DialogTriggerServiceHandle} used to abort keyword spotting
     * @throws DialogTriggerException if any parameter is invalid or a problem occurs
     */
    @Override
    public DialogTriggerServiceHandle spot(DialogTriggerListener dialogTriggerListener, DialogContext dialogContext) throws DialogTriggerException {

        AudioFormat af = VoiceManager.getBestMatch(dialogContext.source().getSupportedFormats(), getSupportedFormats());
        if (af == null) {
            throw new DialogTriggerException("No compatible audio format found for dialog trigger service '" + getId() + "' and source '" + dialogContext.source().getId() +"'" );
        }
        String keyword = dialogContext.keyword();
        if (keyword == null) {
            throw new DialogTriggerException("keyword cannot be null");
        }
        try {
            AudioStream inputStream = dialogContext.source().getInputStream(af);
            //Add to the KS stopping behavior a close of the AudioStream
            DialogTriggerServiceHandle innerHandle = spot(dialogTriggerListener, inputStream, dialogContext.locale(), keyword);
            return () -> {
                try {
                    innerHandle.abort();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
            };
        } catch (AudioException e) {
            throw new DialogTriggerException("Unable to get audio stream from source '" + dialogContext.source().getId() + "'", e);
        }

    }
}
