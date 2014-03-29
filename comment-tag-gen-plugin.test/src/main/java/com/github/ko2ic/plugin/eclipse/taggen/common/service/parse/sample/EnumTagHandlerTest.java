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
package com.github.ko2ic.plugin.eclipse.taggen.common.service.parse.sample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Test;
import org.xml.sax.Attributes;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.sample.EnumItemsTag;

public class EnumTagHandlerTest {

    private final EnumTagHandler target = new EnumTagHandler(new RootTag());

    @Mocked
    private Attributes attributes;

    @Test
    public void ifCall_instanceItemsTag() {
        ItemsTag actual = target.instanceItemsTag();
        assertThat(actual, instanceOf(EnumItemsTag.class));
    }

    @Test
    public void ifCall_setCustomItemsTagValue() {
        EnumItemsTag tag = new EnumItemsTag();

        new NonStrictExpectations() {
            {
                attributes.getLength();
                result = 1;
                times = 2;
                attributes.getQName(0);
                result = "code";
                attributes.getValue(0);
                result = "1";
            }
        };

        target.setCustomItemsTagValue(tag, attributes);

        assertThat(tag.getCode(), is("1"));
    }
}
