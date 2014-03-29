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
package com.github.ko2ic.plugin.eclipse.taggen.core.service.merge;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.google.common.io.CharStreams;

@RunWith(Enclosed.class)
public class EclipseJavaMergerTest {

    private static final String CODE = "public class Test{}";

    @Test
    public void ifTargetIsNull() {
        EclipseJavaMerger engine = new EclipseJavaMerger();
        assertThat(engine.merge(CODE, null), is(CODE));
    }

    @Test
    public void ifSourceAndTargetAreSame() {
        EclipseJavaMerger engine = new EclipseJavaMerger();
        assertThat(engine.merge(CODE, CODE), is(CODE));
    }

    public static class InCaseOfMergeComment {
        private String sourceCode;

        @Before
        public void before() throws UnsupportedEncodingException, IOException {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeComment_source.txt");
            sourceCode = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
        }

        @Test
        public void ifTargetHasHeader() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeComment_ifTargetHasHeader_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeComment_ifTargetHasHeader_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }

        @Test
        public void ifTargetChangeComment() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeComment_ifTargetChangeComment_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeComment_ifTargetChangeComment_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }
    }

    public static class InCaseOfMergeMethod {
        private String sourceCode;

        @Before
        public void before() throws UnsupportedEncodingException, IOException {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_source.txt");
            sourceCode = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
        }

        @Test
        public void ifKeepGeneratedMark() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifKeepGeneratedMark_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifKeepGeneratedMark_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }

        @Test
        public void ifNotKeepGeneratedMark() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifNotKeepGeneratedMark_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifNotKeepGeneratedMark_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }

        @Test
        public void ifDeleteGeneratedMark() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifDeleteGeneratedMark_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifDeleteGeneratedMark_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }

        @Test
        public void ifAddMethod() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifAddMethod_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeMethod_ifAddMethod_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }
    }

    public static class InCaseOfMergeField {
        private String sourceCode;

        @Before
        public void before() throws UnsupportedEncodingException, IOException {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_source.txt");
            sourceCode = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
        }

        @Test
        public void ifKeepGeneratedMark() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifKeepGeneratedMark_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifKeepGeneratedMark_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }

        @Test
        public void ifNotKeepGeneratedMark() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifNotKeepGeneratedMark_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifNotKeepGeneratedMark_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }

        @Test
        public void ifDeleteGeneratedMark() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifDeleteGeneratedMark_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifDeleteGeneratedMark_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }

        @Test
        public void ifAddField() throws UnsupportedEncodingException, IOException {
            EclipseJavaMerger engine = new EclipseJavaMerger();
            InputStream targetStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifAddField_target.txt");
            String targetCode = CharStreams.toString(new InputStreamReader(targetStream, "UTF-8"));

            InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("InCaseOfMergeField_ifAddField_expected.txt");
            String expectedCode = CharStreams.toString(new InputStreamReader(expectedStream, "UTF-8"));

            assertThat(engine.merge(sourceCode, targetCode), is(expectedCode));
        }
    }
}
