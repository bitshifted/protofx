/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

/**
 * Defines preference item with "pending" value. This is a value that is being set for the preference,
 * but isnot stored into persistent storage until explicitly requested.
 *
 * @param <T> type of the preference value
 */
public interface PendingValuePreference<T> {

    /**
     * Sets current value for the preference, but does not persist it.
     *
     * @param value value to set
     */
    void setPendingValue(T value);

    /**
     * Checks is preference has pending value.
     *
     * @return {@code true if pending value is set. {@code false} otherwise
     */
     boolean hasPendingValue();

    /**
     * Resets pending value of the preference.
     */
    void clearPendingValue();

    /**
     * Saves pending value into persistent storage.
     */
    void save();
}
