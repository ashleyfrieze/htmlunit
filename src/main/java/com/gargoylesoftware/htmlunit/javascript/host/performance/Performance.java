/*
 * Copyright (c) 2002-2022 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host.performance;

import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.EDGE;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.FF;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.FF_ESR;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.IE;

import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxClass;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxConstructor;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxFunction;
import com.gargoylesoftware.htmlunit.javascript.configuration.JsxGetter;
import com.gargoylesoftware.htmlunit.javascript.host.event.EventTarget;

/**
 * A JavaScript object for {@code Performance}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@JsxClass({CHROME, EDGE, FF, FF_ESR})
@JsxClass(value = IE, extendedClass = SimpleScriptable.class)
public class Performance extends EventTarget {
    private PerformanceTiming timing_;

    /**
     * Creates an instance.
     */
    @JsxConstructor({CHROME, EDGE, FF, FF_ESR})
    public Performance() {
    }

    /**
     * Returns the {@code navigation} property.
     * @return the {@code navigation} property
     */
    @JsxGetter
    public PerformanceNavigation getNavigation() {
        final PerformanceNavigation navigation = new PerformanceNavigation();
        navigation.setParentScope(getParentScope());
        navigation.setPrototype(getPrototype(navigation.getClass()));
        return navigation;
    }

    /**
     * Returns the {@code timing} property.
     * @return the {@code timing} property
     */
    @JsxGetter
    public PerformanceTiming getTiming() {
        if (timing_ == null) {
            final PerformanceTiming timing = new PerformanceTiming();
            timing.setParentScope(getParentScope());
            timing.setPrototype(getPrototype(timing.getClass()));
            timing_ = timing;
        }

        return timing_;
    }

    /**
     * @return a timestamp
     */
    @JsxFunction
    public double now() {
        return System.nanoTime() / 1_000_000d;
    }
}
