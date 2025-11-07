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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import java.util.Locale;

/**
 * This is the interface for services able to trigger a dialog.
 *
 * @author Gwendal Roulleau - Initial contribution
 */
@NonNullByDefault
public interface DialogTriggerService {

    /**
     * Returns a simple string that uniquely identifies this service
     *
     * @return an id that identifies this service
     */
    String getId();

    /**
     * Returns a localized human readable label that can be used within UIs.
     *
     * @param locale the locale to provide the label for
     * @return a localized string to be used in UIs
     */
    String getLabel(@Nullable Locale locale);

    /**
     * This method starts the process of detecting a dialog trigger
     *
     * The method is supposed to return fast, i.e. it should only start the spotting as a background process.
     *
     * @param dialogTriggerListener Non-null {@link DialogTriggerListener} that {@link DialogTriggerEvent} events target
     * @param dialogContext The {@link DialogContext} describing the dialog to trigger
     * @return A {@link DialogTriggerServiceHandle} used to abort the dialog trigger process
     * @throws DialogTriggerException if any parameter is invalid or a problem occurs
     */
    DialogTriggerServiceHandle spot(DialogTriggerListener dialogTriggerListener, DialogContext dialogContext)
            throws DialogTriggerException;

}
