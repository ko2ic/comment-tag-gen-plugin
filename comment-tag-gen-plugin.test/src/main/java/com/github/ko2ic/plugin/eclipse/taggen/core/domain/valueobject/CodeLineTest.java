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
package com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import lombok.RequiredArgsConstructor;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class CodeLineTest {

    @RunWith(Theories.class)
    public static class InCaseOfCommentTagLine {
        @RequiredArgsConstructor
        public static class Fixture {
            private final String line;

            private final Boolean expected;

        }

        @DataPoints
        public static Fixture[] getParameters() {
            return new Fixture[] {new Fixture("<Root>", true), new Fixture("</Root>", true), new Fixture("<items>", true), new Fixture("</items>", true), new Fixture("<delete>", true),
                    new Fixture("</delete>", true), new Fixture("", false) };
        }

        @Theory
        public void test(Fixture fixture) {
            CodeLine codeLine = new CodeLine(0, fixture.line);
            assertThat(codeLine.isCommentTagLine(), is(fixture.expected));
        }
    }

    @RunWith(Theories.class)
    public static class InCaseOfRootTagLine {
        @RequiredArgsConstructor
        public static class Fixture {
            private final String line;

            private final Boolean expected;
        }

        @DataPoints
        public static Fixture[] getParameters() {
            return new Fixture[] {new Fixture("<Root>", true), new Fixture("</Root>", true), new Fixture("<items>", false), new Fixture("</items>", false), new Fixture("<delete>", false),
                    new Fixture("</delete>", false), new Fixture("", false) };
        }

        @Theory
        public void test(Fixture fixture) {
            CodeLine codeLine = new CodeLine(0, fixture.line);
            assertThat(codeLine.isRootTagLine(), is(fixture.expected));
        }
    }

    @RunWith(Theories.class)
    public static class InCaseOfItemsTagLine {
        @RequiredArgsConstructor
        public static class Fixture {
            private final String line;

            private final Boolean expected;
        }

        @DataPoints
        public static Fixture[] getParameters() {
            return new Fixture[] {new Fixture("<Root>", false), new Fixture("</Root>", false), new Fixture("<items>", true), new Fixture("</items>", true), new Fixture("<delete>", false),
                    new Fixture("</delete>", false), new Fixture("", false) };
        }

        @Theory
        public void test(Fixture fixture) {
            CodeLine codeLine = new CodeLine(0, fixture.line);
            assertThat(codeLine.isItemsTagLine(), is(fixture.expected));
        }
    }

    @RunWith(Theories.class)
    public static class InCaseOfDeleteTagLine {
        @RequiredArgsConstructor
        public static class Fixture {
            private final String line;

            private final Boolean expected;
        }

        @DataPoints
        public static Fixture[] getParameters() {
            return new Fixture[] {new Fixture("<Root>", false), new Fixture("</Root>", false), new Fixture("<items>", false), new Fixture("</items>", false), new Fixture("<delete>", true),
                    new Fixture("</delete>", true), new Fixture("", false) };
        }

        @Theory
        public void test(Fixture fixture) {
            CodeLine codeLine = new CodeLine(0, fixture.line);
            assertThat(codeLine.isDeleteTagLine(), is(fixture.expected));
        }
    }

    @Test
    public void ifCall_replace() {
        CodeLine codeLine = new CodeLine(0, "aaaa bind bbbb");
        CodeLine actual = codeLine.replace("bind", "replace");
        assertThat(actual.toString(), is("aaaa replace bbbb"));
    }
}
