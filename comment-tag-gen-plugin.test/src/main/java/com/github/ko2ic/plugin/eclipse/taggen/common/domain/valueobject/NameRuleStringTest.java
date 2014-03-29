/*******************************************************************************
 * Copyright (c) 2014
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Kouji Ishii - initial implementation
 *******************************************************************************/
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class NameRuleStringTest {

    public static class InCaseOfLowerSnake {

        private static final String VALUE = "lower_snake";

        @Test
        public void ifCall_phraseVariableName() {
            assertThat(new NameRuleString(VALUE).phraseVariableName(), is("lowerSnake"));
        }

        @Test
        public void ifCall_phraseClassName() {
            assertThat(new NameRuleString(VALUE).phraseClassName(), is("LowerSnake"));
        }

        @Test
        public void ifCall_phraseConstantName() {
            assertThat(new NameRuleString(VALUE).phraseConstantName(), is("LOWER_SNAKE"));
        }

        @Test
        public void ifCall_toUpperCamel() {
            assertThat(new NameRuleString(VALUE).toUpperCamel(), is("LowerSnake"));
        }

        @Test
        public void ifCall_toUpperCase() {
            assertThat(new NameRuleString(VALUE).toUpperCase(), is("LOWER_SNAKE"));
        }

        @Test
        public void ifCall_toLowerCase() {
            assertThat(new NameRuleString(VALUE).toLowerCase(), is("lower_snake"));
        }
    }

    public static class InCaseOfUpperSnake {

        private static final String VALUE = "UPPER_SNAKE";

        @Test
        public void ifCall_phraseVariableName() {
            assertThat(new NameRuleString(VALUE).phraseVariableName(), is("upperSnake"));
        }

        @Test
        public void ifCall_phraseClassName() {
            assertThat(new NameRuleString(VALUE).phraseClassName(), is("UpperSnake"));
        }

        @Test
        public void ifCall_phraseConstantName() {
            assertThat(new NameRuleString(VALUE).phraseConstantName(), is("UPPER_SNAKE"));
        }

        @Test
        public void ifCall_toUpperCamel() {
            assertThat(new NameRuleString(VALUE).toUpperCamel(), is("UpperSnake"));
        }

        @Test
        public void ifCall_toUpperCase() {
            assertThat(new NameRuleString(VALUE).toUpperCase(), is("UPPER_SNAKE"));
        }

        @Test
        public void ifCall_toLowerCase() {
            assertThat(new NameRuleString(VALUE).toLowerCase(), is("upper_snake"));
        }
    }

    public static class InCaseOfLowerCamel {

        private static final String VALUE = "LowerCamel";

        @Test
        public void ifCall_phraseVariableName() {
            assertThat(new NameRuleString(VALUE).phraseVariableName(), is("lowerCamel"));
        }

        @Test
        public void ifCall_phraseClassName() {
            assertThat(new NameRuleString(VALUE).phraseClassName(), is("LowerCamel"));
        }

        @Test
        public void ifCall_phraseConstantName() {
            assertThat(new NameRuleString(VALUE).phraseConstantName(), is("LOWER_CAMEL"));
        }

        @Test
        public void ifCall_toUpperCamel() {
            assertThat(new NameRuleString(VALUE).toUpperCamel(), is("LowerCamel"));
        }

        @Test
        public void ifCall_toUpperCase() {
            assertThat(new NameRuleString(VALUE).toUpperCase(), is("LOWERCAMEL"));
        }

        @Test
        public void ifCall_toLowerCase() {
            assertThat(new NameRuleString(VALUE).toLowerCase(), is("lowercamel"));
        }
    }

    public static class InCaseOfUpperCamel {

        private static final String VALUE = "UpperCamel";

        @Test
        public void ifCall_phraseVariableName() {
            assertThat(new NameRuleString(VALUE).phraseVariableName(), is("upperCamel"));
        }

        @Test
        public void ifCall_phraseClassName() {
            assertThat(new NameRuleString(VALUE).phraseClassName(), is("UpperCamel"));
        }

        @Test
        public void ifCall_phraseConstantName() {
            assertThat(new NameRuleString(VALUE).phraseConstantName(), is("UPPER_CAMEL"));
        }

        @Test
        public void ifCall_toUpperCamel() {
            assertThat(new NameRuleString(VALUE).toUpperCamel(), is("UpperCamel"));
        }

        @Test
        public void ifCall_toUpperCase() {
            assertThat(new NameRuleString(VALUE).toUpperCase(), is("UPPERCAMEL"));
        }

        @Test
        public void ifCall_toLowerCase() {
            assertThat(new NameRuleString(VALUE).toLowerCase(), is("uppercamel"));
        }
    }

}
