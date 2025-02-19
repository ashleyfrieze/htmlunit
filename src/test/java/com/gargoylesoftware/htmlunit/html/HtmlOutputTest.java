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
package com.gargoylesoftware.htmlunit.html;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.junit.BrowserRunner;
import com.gargoylesoftware.htmlunit.junit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.junit.BrowserRunner.HtmlUnitNYI;

/**
 * Tests for {@link HtmlOutput}.
 *
 * @author Ronald Brill
 */
@RunWith(BrowserRunner.class)
public class HtmlOutputTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object HTMLOutputElement]", "[object HTMLFormElement]"},
            IE = {"[object HTMLUnknownElement]", "undefined"})
    public void simpleScriptable() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + LOG_TITLE_FUNCTION
            + "  function test() {\n"
            + "    var o = document.getElementById('o');\n"
            + "    log(o);\n"
            + "    log(o.form);\n"
            + "  }\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "  <form>\n"
            + "    <output id='o'>\n"
            + "  </form>\n"
            + "</body></html>";
        final WebDriver driver = loadPageVerifyTitle2(html);
        if (driver instanceof HtmlUnitDriver) {
            final HtmlElement element = toHtmlElement(driver.findElement(By.id("o")));
            assertTrue(element instanceof HtmlOutput || element instanceof HtmlUnknownElement);
        }
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"undefined", "undefined", "undefined", "center", "8", "foo"})
    public void align() throws Exception {
        final String html
            = "<html><body>\n"
            + "<form>\n"
            + "  <output id='o1' align='left'>\n"
            + "  <output id='o2' align='right'>\n"
            + "  <output id='o3' align='3'>\n"
            + "</form>\n"
            + "<script>\n"
            + LOG_TITLE_FUNCTION
            + "  function set(fs, value) {\n"
            + "    try {\n"
            + "      fs.align = value;\n"
            + "    } catch (e) {\n"
            + "      log('error');\n"
            + "    }\n"
            + "  }\n"
            + "  var o1 = document.getElementById('o1');\n"
            + "  var o2 = document.getElementById('o2');\n"
            + "  var o3 = document.getElementById('o3');\n"
            + "  log(o1.align);\n"
            + "  log(o2.align);\n"
            + "  log(o3.align);\n"
            + "  set(o1, 'center');\n"
            + "  set(o2, '8');\n"
            + "  set(o3, 'foo');\n"
            + "  log(o1.align);\n"
            + "  log(o2.align);\n"
            + "  log(o3.align);\n"
            + "</script>\n"
            + "</body></html>";

        loadPageVerifyTitle2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"true", "true", "true", "true", "true"},
            FF_ESR = {"true", "true", "false", "false", "true"},
            IE = "no checkValidity()")
    @HtmlUnitNYI(FF_ESR = {"true", "true", "true", "true", "true"})
    public void checkValidity() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + LOG_TITLE_FUNCTION
            + "  function test() {\n"
            + "    var foo = document.getElementById('foo');\n"

            + "    if (!foo.checkValidity) {log('no checkValidity()'); return;}\n"

            + "    log(foo.checkValidity());\n"

            + "    foo.setCustomValidity('');\n"
            + "    log(foo.checkValidity());\n"

            + "    foo.setCustomValidity(' ');\n"
            + "    log(foo.checkValidity());\n"

            + "    foo.setCustomValidity('invalid');\n"
            + "    log(foo.checkValidity());\n"

            + "    foo.setCustomValidity('');\n"
            + "    log(foo.checkValidity());\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <output id='foo'>\n"
            + "</body>\n"
            + "</html>";

        loadPageVerifyTitle2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"false", "false", "false", "false", "false"},
            FF_ESR = {"true", "true", "true", "true", "true"},
            IE = {"undefined", "undefined", "undefined", "undefined", "undefined"})
    public void willValidate() throws Exception {
        final String html =
                "<html><head>\n"
                + "  <script>\n"
                + LOG_TITLE_FUNCTION
                + "    function test() {\n"
                + "      log(document.getElementById('o1').willValidate);\n"
                + "      log(document.getElementById('o2').willValidate);\n"
                + "      log(document.getElementById('o3').willValidate);\n"
                + "      log(document.getElementById('o4').willValidate);\n"
                + "      log(document.getElementById('o5').willValidate);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form>\n"
                + "    <output id='o1'>o1</output>\n"
                + "    <output id='o2' disabled>o2</output>\n"
                + "    <output id='o3' hidden>o3</output>\n"
                + "    <output id='o4' readonly>o4</output>\n"
                + "    <output id='o5' style='display: none'>o5</output>\n"
                + "  </form>\n"
                + "</body></html>";

        loadPageVerifyTitle2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = "false-false-false-false-false-false-false-false-false-true-false",
            IE = "no validity")
    public void validityState() throws Exception {
        final String html =
                "<html><head>\n"
                + "  <script>\n"
                + LOG_TITLE_FUNCTION
                + "    function logValidityState(s) {\n"
                + "      log(s.badInput"
                        + "+ '-' + s.customError"
                        + "+ '-' + s.patternMismatch"
                        + "+ '-' + s.rangeOverflow"
                        + "+ '-' + s.rangeUnderflow"
                        + "+ '-' + s.stepMismatch"
                        + "+ '-' + s.tooLong"
                        + "+ '-' + s.tooShort"
                        + " + '-' + s.typeMismatch"
                        + " + '-' + s.valid"
                        + " + '-' + s.valueMissing);\n"
                + "    }\n"
                + "    function test() {\n"
                + "      var elem = document.getElementById('o1');\n"
                + "      if (!elem.validity) { log('no validity'); return }\n"
                + "      logValidityState(elem.validity);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form>\n"
                + "    <output id='o1'>o1</output>\n"
                + "  </form>\n"
                + "</body></html>";

        loadPageVerifyTitle2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"false-false-false-false-false-false-false-false-false-true-false",
                       "false-true-false-false-false-false-false-false-false-false-false"},
            IE = "no validity")
    public void validityStateCustomValidity() throws Exception {
        final String html =
                "<html><head>\n"
                + "  <script>\n"
                + LOG_TITLE_FUNCTION
                + "    function logValidityState(s) {\n"
                + "      log(s.badInput"
                        + "+ '-' + s.customError"
                        + "+ '-' + s.patternMismatch"
                        + "+ '-' + s.rangeOverflow"
                        + "+ '-' + s.rangeUnderflow"
                        + "+ '-' + s.stepMismatch"
                        + "+ '-' + s.tooLong"
                        + "+ '-' + s.tooShort"
                        + " + '-' + s.typeMismatch"
                        + " + '-' + s.valid"
                        + " + '-' + s.valueMissing);\n"
                + "    }\n"
                + "    function test() {\n"
                + "      var elem = document.getElementById('o1');\n"
                + "      var validity = elem.validity;\n"
                + "      if (!elem.validity) { log('no validity'); return }\n"
                + "      logValidityState(validity);\n"
                + "      elem.setCustomValidity('Invalid');\n"
                + "      logValidityState(validity);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form>\n"
                + "    <output id='o1'>o1</output>\n"
                + "  </form>\n"
                + "</body></html>";

        loadPageVerifyTitle2(html);
    }
}
