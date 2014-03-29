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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ItemsTagTest {

    private final ItemsTag target = new ItemsTag();

    @Test
    public void ifCall_interchange() {
        target.setCamelCase("camelCase");
        target.setEnd(";");
        target.setSeparator(",");
        target.setUpperCase("upperCase");
        target.setContents("contents");

        ItemsTag arg = new ItemsTag();
        arg.setCamelCase("camelCase2");
        arg.setEnd(";2");
        arg.setSeparator(",2");
        arg.setUpperCase("upperCase2");
        arg.setContents("contents2");

        target.interchange(arg);
        assertThat(target.getCamelCase(), is("camelCase2"));
        assertThat(target.getEnd(), is(";2"));
        assertThat(target.getSeparator(), is(",2"));
        assertThat(target.getUpperCase(), is("upperCase2"));

        assertThat(target.getContents(), is("contents"));
    }

}
