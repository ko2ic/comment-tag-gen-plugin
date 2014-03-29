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

import org.xml.sax.Attributes;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.sample.EnumItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.service.parse.TagHandlerBase;

/**
 * Sets custom attributes of tag for enum.
 * @author ko2ic
 */
public class EnumTagHandler extends TagHandlerBase {

    public EnumTagHandler(RootTag rootTag) {
        super(rootTag);
    }

    @Override
    protected ItemsTag instanceItemsTag() {
        return new EnumItemsTag();
    }

    @Override
    protected void setCustomRootTagValue(RootTag rootTag, Attributes attributes) {
    }

    @Override
    protected void setCustomItemsTagValue(ItemsTag itemsTag, Attributes attributes) {
        EnumItemsTag enumTag = (EnumItemsTag) itemsTag;
        for (int i = 0; i < attributes.getLength(); i++) {
            if ("code".equalsIgnoreCase(attributes.getQName(i))) {
                enumTag.setCode(attributes.getValue(i));
            }
        }
    }

}
