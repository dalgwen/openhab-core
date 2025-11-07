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

import java.io.Serial;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * General purpose dialog trigger exception
 *
 * @author Kelly Davis - Initial contribution
 */
@NonNullByDefault
public class DialogTriggerException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with null as its detail message.
     */
    public DialogTriggerException() {
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message Detail message
     * @param cause The cause
     */
    public DialogTriggerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message Detail message
     */
    public DialogTriggerException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause The cause
     */
    public DialogTriggerException(Throwable cause) {
        super(cause);
    }
}
