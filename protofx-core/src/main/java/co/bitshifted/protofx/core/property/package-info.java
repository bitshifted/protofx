/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 * Classes in this package are used to wrap POJO fields into observable properties that can notify
 * listeners on change.
 *
 * <p>Example usage of property backed by a class field:
 *
 * <pre>
 *     <code>
 *         // Simple POJO class
 *         class Foo {
 *             private String name;
 *             private int count;
 *             // getters and setter
 *         }
 *         // create String property backed by name field
 *         var prop = FieldBackedPropertyGenerator.stringFieldProperty(foo::getName, foo::setName);
 *         // listener can be added to a property, orbinding can be applied
 *         prop.addListener((observable, oldValue, newValue) -> {
 *          System.out.println("Name changed from " + oldValue + " to " + newValue);
 *         }
 *     </code>
 * </pre>
 */
package co.bitshifted.protofx.core.property;
