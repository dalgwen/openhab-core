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

import java.util.List;
import java.util.Locale;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Describes dialog desired services and options.
 *
 * @author Miguel Álvarez - Initial contribution
 */
@NonNullByDefault
public class DialogRegistration {

    /**
     * Dialog audio source id
     */
    public String sourceId;
    /**
     * Dialog audio sink id
     */
    public String sinkId;
    /**
     * Preferred dialog-triggering service
     */
    public List<String> dtsId = List.of();
    /**
     * Selected keyword for spotting
     */
    public @Nullable String keyword;
    /**
     * Preferred speech-to-text service id
     */
    public @Nullable String sttId;
    /**
     * Preferred text-to-speech service id
     */
    public @Nullable String ttsId;
    /**
     * Preferred voice id
     */
    public @Nullable String voiceId;
    /**
     * List of interpreters
     */
    public List<String> hliIds = List.of();
    /**
     * Dialog locale
     */
    public @Nullable Locale locale;
    /**
     * Linked listening item
     */
    public @Nullable String listeningItem;
    /**
     * Linked location item
     */
    public @Nullable String locationItem;
    /**
     * Dialog group name
     */
    public @Nullable String dialogGroup;
    /**
     * Custom listening melody
     */
    public @Nullable String listeningMelody;
    /**
     * True if an associated dialog is running
     */
    public boolean running = false;

    /**
     * True if the dialog should be enabled.
     */
    public boolean enabled;

    public DialogRegistration(String sourceId, String sinkId) {
        this.sourceId = sourceId;
        this.sinkId = sinkId;
    }
}
