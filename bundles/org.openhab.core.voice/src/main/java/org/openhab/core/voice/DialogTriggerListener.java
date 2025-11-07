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

/**
 * The listener interface for receiving {@link DialogTriggerEvent} events.
 *
 * A class interested in processing {@link DialogTriggerEvent} events implements this interface,
 * and its instances are passed to the {@code KSService}'s {@code spot()} method.
 * Such instances are then targeted for various {@link DialogTriggerEvent} events corresponding
 * to the keyword spotting process.
 *
 * @author Kelly Davis - Initial contribution
 */
@NonNullByDefault
public interface DialogTriggerListener {
    /**
     * Invoked when a {@link DialogTriggerEvent} event occurs during keyword spotting.
     *
     * @param dialogTriggerEvent The {@link DialogTriggerEvent} fired by the {@link KSService}
     */
    void dialogTriggerEventReceived(DialogTriggerEvent dialogTriggerEvent);
}
