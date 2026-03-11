/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import java.io.Serializable;
import java.util.Collection;

/**
 * Defines methods for saving user preferences within the application. Example usage:
 * <code>
 *     var pref = manager.getStringPReferenceEntry("my.pref", "foo"); // if preference value is not set, returnd default value "foo"
 *     var value = pref.getValue(); // returns "foo"
 *     pref.setPendingValue("bar"); // value is now "bar", but is not persisted
 *     pref.save(); // bar is noe persisted
 *     pref.save("baz"); // persist value "baz" immediatelly
 * </code>
 *
 * All preference entries encapsulate JavaFX {@code Property}, which can accept listeners and notify them of changes in value. This allows
 * clients to react to changes in preference values.
 *
 * <code>
 *     var pref = manager.getStringPReferenceEntry("my.pref", "foo");
 *     pref.getProperty().addListener(new ChangeListener<String>() {
 * </code>
 */
public interface PreferenceManager {

    IntegerPreferenceEntry getIntegerPreference(String root, String name, Integer defaultValue);

    IntegerPreferenceEntry getIntegerPreference(String name, Integer defaultValue);

    LongPreferenceEntry getLongPreference(String root, String name, Long defaultValue);

    LongPreferenceEntry getLongPreference(String name, Long defaultValue);

    FloatPreferenceEntry getFloatPreference(String root, String name, Float defaultValue);

    FloatPreferenceEntry getFloatPreference(String name, Float defaultValue);

    DoublePreferenceEntry getDoublePreferenceEntry(String root, String name, Double defaultValue);

    DoublePreferenceEntry getDoublePreferenceEntry(String name, Double defaultValue);

    StringPreferenceEntry getStringPreferenceEntry(String root, String name, String defaultValue);

    StringPreferenceEntry getStringPreferenceEntry(String name, String defaultValue);

    BooleanPreferenceEntry getBooleanPreferenceEntry(String root, String name, Boolean defaultValue);

    BooleanPreferenceEntry getBooleanPreferenceEntry( String name, Boolean defaultValue);

    <T extends Serializable>  MultiValuePreferenceEntry<T> getMultiValuePreferenceEntry(String root, String name, Collection<T> defaultValue);

    <T extends Serializable>  MultiValuePreferenceEntry<T> getMultiValuePreferenceEntry(String name, Collection<T> defaultValue);

}
