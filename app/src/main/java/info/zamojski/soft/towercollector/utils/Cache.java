/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.utils;

import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.PNL.ParameterizedContainer;
import org.checkerframework.checker.PNL.ParameterizedContainerAddition;

@ParameterizedContainer
public class Cache<TKey, TValue> {

    private final Map<TKey, TValue> cache = new HashMap<>();

    public TValue get(TKey key) {
        return cache.get(key);
    }

    @ParameterizedContainerAddition(elementIndex = {0, 1}, typeArgumentIndex = {0, 1})
    public void set(TKey key, TValue value) {
        cache.put(key, value);
    }

    public void invalidate(TKey key) {
        cache.remove(key);
    }
}
