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

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample.EnumCodeSeed.EnumClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.google.common.base.Strings;

/**
 * Presents &lt;items&gt; tag for enum.
 * @author ko2ic
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EnumItemsTag extends ItemsTag {

    private String code;

    /**
     * Replace contents surrounded by &lt;items&gt;contents&lt;/items&gt;tag. <br/>
     * tag will be to have replacements contents..
     * @param enumAttributes object from spreadsheet
     */
    @Override
    public void replaceContent(List<? extends ClassElementsItem> items) {
        @SuppressWarnings("unchecked")
        List<EnumClassElementsItem> enumItems = (List<EnumClassElementsItem>) items;
        StringBuilder sb = new StringBuilder();
        for (EnumClassElementsItem enumAttribute : enumItems) {
            String newContents = getSrcContents().replaceAll(getUpperCase(), enumAttribute.getUpperName());
            newContents = newContents.replaceAll(getCamelCase(), enumAttribute.getUpperCamelName());
            newContents = newContents.replaceAll(code, enumAttribute.getCode());
            sb.append(newContents);
        }
        if (!Strings.isNullOrEmpty(getEnd())) {
            int lastComma = sb.toString().lastIndexOf(getSeparator());
            if (lastComma != -1) {
                sb.replace(lastComma, lastComma + 1, getEnd());
            }
        }
        setContents(sb.toString());
    }

    @Override
    public void interchange(ItemsTag srcTag) {
        super.interchange(srcTag);
        setCode(((EnumItemsTag) srcTag).getCode());
    }
}
