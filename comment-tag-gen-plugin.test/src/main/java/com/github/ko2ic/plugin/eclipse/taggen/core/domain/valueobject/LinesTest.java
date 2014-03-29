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

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;

public class LinesTest {

    Lines target = new Lines();

    @Before
    public void before() {
        target.add("before tag");
        target.add("<root>");
        target.add("contents");
        target.add("</root>");
        target.add("after tag");
    }

    @Test
    public void ifCall_inTag() {

        RootTag rootTag = new RootTag();
        rootTag.setStartLineNumber(2);
        rootTag.setEndLineNumber(4);

        assertThat(target.inTag(rootTag, 1), is(false));
        assertThat(target.inTag(rootTag, 2), is(false));
        assertThat(target.inTag(rootTag, 3), is(true));
        assertThat(target.inTag(rootTag, 4), is(false));
        assertThat(target.inTag(rootTag, 4), is(false));
    }

    @Test
    public void ifCall_getAllLines() {
        List<CodeLine> lines = target.getAllLines();
        assertThat(lines.size(), is(5));
    }

    @Test
    public void ifCall_get() {
        CodeLine actual = target.get(1);
        assertThat(actual.toString(), is("before tag" + System.getProperty("line.separator")));
    }

    public void ifCall_toString() {
        String br = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append("before tag").append(br);
        sb.append("<root>").append(br);
        sb.append("contents").append(br);
        sb.append("</root>").append(br);
        sb.append("after tag").append(br);

        assertThat(target.toString(), is(sb.toString()));
    }
}
