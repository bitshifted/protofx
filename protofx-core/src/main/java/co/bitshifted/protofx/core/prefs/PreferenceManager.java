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
 * Defines methods for saving user preferences within the application. Example usage: <code>
 *     var pref = manager.getStringPReferenceEntry("my.pref", "foo"); // if preference value is not set, returnd default value "foo"
 *     var value = pref.getValue(); // returns "foo"
 *     pref.setPendingValue("bar"); // value is now "bar", but is not persisted
 *     pref.save(); // bar is noe persisted
 *     pref.save("baz"); // persist value "baz" immediatelly
 * </code> All preference entries encapsulate JavaFX {@code Property}, which can accept listeners
 * and notify them of changes in value. This allows clients to react to changes in preference
 * values. <code>
 *     var pref = manager.getStringPReferenceEntry("my.pref", "foo");
 *     pref.getProperty().addListener(new ChangeListener<String>());
 * </code>
 */
public interface PreferenceManager {

  /**
   * Returns an integer preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the integer preference entry
   */
  IntegerPreferenceEntry getIntegerPreference(String root, String name, Integer defaultValue);

  /**
   * Returns an integer preference entry using the default root node.
   *
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the integer preference entry
   */
  IntegerPreferenceEntry getIntegerPreference(String name, Integer defaultValue);

  /**
   * Returns a long preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the long preference entry
   */
  LongPreferenceEntry getLongPreference(String root, String name, Long defaultValue);

  /**
   * Returns a long preference entry using the default root node.
   *
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the long preference entry
   */
  LongPreferenceEntry getLongPreference(String name, Long defaultValue);

  /**
   * Returns a float preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the float preference entry
   */
  FloatPreferenceEntry getFloatPreference(String root, String name, Float defaultValue);

  /**
   * Returns a float preference entry using the default root node.
   *
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the float preference entry
   */
  FloatPreferenceEntry getFloatPreference(String name, Float defaultValue);

  /**
   * Returns a double preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the double preference entry
   */
  DoublePreferenceEntry getDoublePreferenceEntry(String root, String name, Double defaultValue);

  /**
   * Returns a double preference entry using the default root node.
   *
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the double preference entry
   */
  DoublePreferenceEntry getDoublePreferenceEntry(String name, Double defaultValue);

  /**
   * Returns a string preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the string preference entry
   */
  StringPreferenceEntry getStringPreferenceEntry(String root, String name, String defaultValue);

  /**
   * Returns a string preference entry using the default root node.
   *
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the string preference entry
   */
  StringPreferenceEntry getStringPreferenceEntry(String name, String defaultValue);

  /**
   * Returns a boolean preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the boolean preference entry
   */
  BooleanPreferenceEntry getBooleanPreferenceEntry(String root, String name, Boolean defaultValue);

  /**
   * Returns a boolean preference entry using the default root node.
   *
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @return the boolean preference entry
   */
  BooleanPreferenceEntry getBooleanPreferenceEntry(String name, Boolean defaultValue);

  /**
   * Returns a multi-value preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @param <T> the type of the values
   * @return the multi-value preference entry
   */
  <T extends Serializable> MultiValuePreferenceEntry<T> getMultiValuePreferenceEntry(
      String root, String name, Collection<T> defaultValue);

  /**
   * Returns a multi-value preference entry using the default root node.
   *
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   * @param <T> the type of the values
   * @return the multi-value preference entry
   */
  <T extends Serializable> MultiValuePreferenceEntry<T> getMultiValuePreferenceEntry(
      String name, Collection<T> defaultValue);
}
