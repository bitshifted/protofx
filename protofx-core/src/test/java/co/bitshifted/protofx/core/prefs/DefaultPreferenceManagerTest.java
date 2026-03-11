/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import co.bitshifted.protofx.core.di.DefaultApplicationConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.UUID;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

class DefaultPreferenceManagerTest {

    private String rootNode;
    private PreferenceManager manager;

    @BeforeEach
    void setup() {
        rootNode = UUID.randomUUID().toString();
        manager =  new DefaultPreferenceManager(new DefaultApplicationConfig());
    }

    @AfterEach
    void cleanup() throws Exception {
        var node = Preferences.userRoot().node(rootNode);
        node.removeNode();
        var defaultNode = Preferences.userRoot().node(DefaultApplicationConfig.class.getPackageName());
        defaultNode.removeNode();
    }


    @Test
    void getIntegerPreferenceSuccess() {
        var entry = manager.getIntegerPreference("my/root", "pref-name", 5);
        assertNotNull(entry);
        assertEquals(5, entry.getValue());
    }

    @Test
    void saveIntegerPreferenceSuccess() {
        var entry = manager.getIntegerPreference(rootNode, "test-entry", 12);
        entry.save();
        var node = Preferences.userRoot().node(rootNode);
        var savedValue = node.getInt("test-entry", 0);
        assertEquals(12, savedValue);
        // update value
        entry.save(15);
        savedValue = node.getInt("test-entry", 0);
        assertEquals(15, savedValue);
    }

    @Test
    void getStringPreferenceSuccess() {
        var entry = manager.getStringPreferenceEntry("pref-name", "default");
        assertNotNull(entry);
        assertEquals("default", entry.getValue());
    }

    @Test
    void saveStringPreferenceSuccess() {
        var entry = manager.getStringPreferenceEntry("test-entry", "default");
        entry.save();
        var node = Preferences.userRoot().node(DefaultApplicationConfig.class.getPackageName());
        var savedValue = node.get("test-entry", null);
        assertEquals("default", savedValue);
        // update value
        entry.save("new-value");
        savedValue = node.get("test-entry", null);
        assertEquals("new-value", savedValue);
    }

    @Test
    void getStringPreferenceEntryWithRoot() {
        var entry = manager.getStringPreferenceEntry(rootNode, "test-entry", "default");
        assertNotNull(entry);
        assertEquals("default", entry.getValue());
    }

    @Test
    void saveStringPreferenceEntryWithRoot() throws Exception {
        var entry = manager.getStringPreferenceEntry(rootNode, "test-entry", "default");
        entry.save("new-value");
        var node = Preferences.userRoot().node(rootNode);
        var value = node.get("test-entry", "default");
        assertEquals("new-value", value);
        node.removeNode();
    }

    @Test
    void getLongPreferenceSuccess() {
        var entry = manager.getLongPreference("pref-name", 10L);
        assertNotNull(entry);
        assertEquals(10L, entry.getValue());
    }

    @Test
    void saveLongPreferenceSuccess() {
        var entry = manager.getLongPreference("test-entry", 20L);
        entry.save();
        var node = Preferences.userRoot().node(DefaultApplicationConfig.class.getPackageName());
        var savedValue = node.getLong("test-entry", 0L);
        assertEquals(20L, savedValue);
        // update value
        entry.save(30L);
        savedValue = node.getLong("test-entry", 0L);
        assertEquals(30L, savedValue);
    }

    @Test
    void getLongPreferenceEntryWithRoot() {
        var entry = manager.getLongPreference(rootNode, "test-entry", 40L);
        assertNotNull(entry);
        assertEquals(40L, entry.getValue());
    }

    @Test
    void saveLongPreferenceEntryWithRoot() throws Exception {
        var entry = manager.getLongPreference(rootNode, "test-entry", 50L);
        entry.save(60L);
        var node = Preferences.userRoot().node(rootNode);
        var value = node.getLong("test-entry", 0L);
        assertEquals(60L, value);
        node.removeNode();
    }

    @Test
    void getBooleanPreferenceSuccess() {
        var entry = manager.getBooleanPreferenceEntry("pref-name", true);
        assertNotNull(entry);
        assertTrue(entry.getValue());
    }

    @Test
    void saveBooleanPreferenceSuccess() {
        var entry = manager.getBooleanPreferenceEntry("test-entry", true);
        entry.save();
        var node = Preferences.userRoot().node(DefaultApplicationConfig.class.getPackageName());
        var savedValue = node.getBoolean("test-entry", false);
        assertTrue(savedValue);
        // update value
        entry.save(false);
        savedValue = node.getBoolean("test-entry", true);
        assertFalse(savedValue);
    }

    @Test
    void getBooleanPreferenceEntryWithRoot() {
        var entry = manager.getBooleanPreferenceEntry(rootNode, "test-entry", true);
        assertNotNull(entry);
        assertTrue(entry.getValue());
    }

    @Test
    void saveBooleanPreferenceEntryWithRoot() throws Exception {
        var entry = manager.getBooleanPreferenceEntry(rootNode, "test-entry", true);
        entry.save(false);
        var node = Preferences.userRoot().node(rootNode);
        var value = node.getBoolean("test-entry", true);
        assertFalse(value);
        node.removeNode();
    }

    @Test
    void getDoublePreferenceSuccess() {
        var entry = manager.getDoublePreferenceEntry("pref-name", 1.0);
        assertNotNull(entry);
        assertEquals(1.0, entry.getValue());
    }

    @Test
    void saveDoublePreferenceSuccess() {
        var entry = manager.getDoublePreferenceEntry("test-entry", 2.0);
        entry.save();
        var node = Preferences.userRoot().node(DefaultApplicationConfig.class.getPackageName());
        var savedValue = node.getDouble("test-entry", 0.0);
        assertEquals(2.0, savedValue);
        // update value
        entry.save(3.0);
        savedValue = node.getDouble("test-entry", 0.0);
        assertEquals(3.0, savedValue);
    }

    @Test
    void getDoublePreferenceEntryWithRoot() {
        var entry = manager.getDoublePreferenceEntry(rootNode, "test-entry", 4.0);
        assertNotNull(entry);
        assertEquals(4.0, entry.getValue());
    }

    @Test
    void saveDoublePreferenceEntryWithRoot() throws Exception {
        var entry = manager.getDoublePreferenceEntry(rootNode, "test-entry", 5.0);
        entry.save(6.0);
        var node = Preferences.userRoot().node(rootNode);
        var value = node.getDouble("test-entry", 0.0);
        assertEquals(6.0, value);
        node.removeNode();
    }

    @Test
    void getFloatPreferenceSuccess() {
        var entry = manager.getFloatPreference("pref-name", 1.0f);
        assertNotNull(entry);
        assertEquals(1.0f, entry.getValue());
    }

    @Test
    void saveFloatPreferenceSuccess() {
        var entry = manager.getFloatPreference("test-entry", 2.0f);
        entry.save();
        var node = Preferences.userRoot().node(DefaultApplicationConfig.class.getPackageName());
        var savedValue = node.getFloat("test-entry", 0.0f);
        assertEquals(2.0f, savedValue);
        // update value
        entry.save(3.0f);
        savedValue = node.getFloat("test-entry", 0.0f);
        assertEquals(3.0f, savedValue);
    }

    @Test
    void getFloatPreferenceEntryWithRoot() {
        var entry = manager.getFloatPreference(rootNode, "test-entry", 4.0f);
        assertNotNull(entry);
        assertEquals(4.0f, entry.getValue());
    }

    @Test
    void saveFloatPreferenceEntryWithRoot() throws Exception {
        var entry = manager.getFloatPreference(rootNode, "test-entry", 5.0f);
        entry.save(6.0f);
        var node = Preferences.userRoot().node(rootNode);
        var value = node.getFloat("test-entry", 0.0f);
        assertEquals(6.0f, value);
        node.removeNode();
    }

    @Test
    void updatePreferenceTriggersListener() throws Exception {
//        var manager = new DefaultPreferenceManager(new DefaultApplicationConfig());
        var entry = manager.getIntegerPreference(rootNode, "test-entry", 12);
        var listener = new TestListener();
        entry.getProperty().addListener(listener);
        entry.save(20);
        var node = Preferences.userRoot().node(rootNode);
        var savedValue = node.getInt("test-entry", 0);
        assertEquals(20, savedValue);
        assertEquals(1, listener.getCount());
    }

    @Test
    void saveMultiValuePreferenceSuccess() throws Exception {
//        var manager = new DefaultPreferenceManager(new DefaultApplicationConfig());
        var defaultValue = List.of(new FooRecord("name", 10));
        var entry = manager.getMultiValuePreferenceEntry(rootNode, "multi-val-test", defaultValue);
        assertEquals(1, entry.getValue().size());
        var out = entry.getValue().iterator().next();
        assertEquals("name", out.name());
        assertEquals(10, out.number());
        // update value
        var newVal = List.of(new FooRecord("foo1", 10), new FooRecord("foo2", 20));
        entry.getProperty().setValue(newVal);
        entry.save();
        // verify preference state
        var prefsNode = Preferences.userRoot().node(rootNode);
        var data = prefsNode.getByteArray("multi-val-test", new byte[0]);
        try(var ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            var prefList = (List<FooRecord>)ois.readObject();
            assertEquals(2, prefList.size());
            var foo1 = prefList.get(0);
            assertEquals("foo1", foo1.name());
            assertEquals(10, foo1.number());
        }
    }

    public class TestListener implements ChangeListener<Number> {

        private int count = 0;

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number integer, Number t1) {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}
