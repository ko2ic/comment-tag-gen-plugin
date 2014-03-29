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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.sample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample.EnumCodeSeed.EnumClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject.NameRuleString;

public class EnumItemsTagTest {

    private final EnumItemsTag target = new EnumItemsTag();

    @Test
    public void ifCall_replaceContent() {

        target.setContents("contents UPPER camelCase code,");
        target.setUpperCase("UPPER");
        target.setCamelCase("camelCase");
        target.setCode("code");
        target.setEnd(";");
        target.setSeparator(",");

        List<ClassElementsItem> items = new ArrayList<>();
        items.add(new EnumClassElementsItem(new NameRuleString("name1"), "1", "comment1"));
        items.add(new EnumClassElementsItem(new NameRuleString("name2"), "2", "comment2"));

        target.replaceContent(items);

        String actual = target.getContents();

        assertThat(actual, is("contents NAME1 Name1 1,contents NAME2 Name2 2;"));
    }

}
