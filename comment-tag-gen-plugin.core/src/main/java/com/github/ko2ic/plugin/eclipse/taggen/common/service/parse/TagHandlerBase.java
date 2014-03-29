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
package com.github.ko2ic.plugin.eclipse.taggen.common.service.parse;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.CommentTag.TagType;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.DeleteTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;

/**
 * Handles Tag with SaxParser<br>
 * @author ko2ic
 */
public abstract class TagHandlerBase extends DefaultHandler {

    private static final String CLASS_NAME_ATTR_NAME = "className";

    private static final String CLASS_COMMENT_ATTR_NAME = "classComment";

    private static final String UPPER_CASE_ATTR_NAME = "upperCase";

    private static final String CAMEL_CASE_ATTR_NAME = "camelCase";

    private static final String SEPARATOR_ATTR_NAME = "separator";

    private static final String END_ATTR_NAME = "end";

    private final RootTag rootTag;

    private final List<ItemsTag> itemsTags = new ArrayList<>();

    private final List<DeleteTag> deleteTags = new ArrayList<>();

    public TagHandlerBase(RootTag rootTag) {
        this.rootTag = rootTag;
    }

    public RootTag getRootTag() {
        return rootTag;
    }

    /**
     * itemsTagを取得する<br>
     * @return itemsTag
     */
    public List<? extends ItemsTag> getItemsTag() {
        return itemsTags;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (TagType.ROOT.equals(qName)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (CLASS_NAME_ATTR_NAME.equalsIgnoreCase(attributes.getQName(i))) {
                    rootTag.setClassName(attributes.getValue(i));
                } else if (CLASS_COMMENT_ATTR_NAME.equalsIgnoreCase(attributes.getQName(i))) {
                    rootTag.setClassComment(attributes.getValue(i));
                }
            }
            setCustomRootTagValue(rootTag, attributes);
        } else {
            if (TagType.ITEMS.equals(qName)) {
                ItemsTag itemsTag = instanceItemsTag();
                for (int i = 0; i < attributes.getLength(); i++) {
                    if (UPPER_CASE_ATTR_NAME.equalsIgnoreCase(attributes.getQName(i))) {
                        itemsTag.setUpperCase(attributes.getValue(i));
                    } else if (CAMEL_CASE_ATTR_NAME.equalsIgnoreCase(attributes.getQName(i))) {
                        itemsTag.setCamelCase(attributes.getValue(i));

                    } else if (SEPARATOR_ATTR_NAME.equalsIgnoreCase(attributes.getQName(i))) {
                        itemsTag.setSeparator(attributes.getValue(i));
                    } else if (END_ATTR_NAME.equalsIgnoreCase(attributes.getQName(i))) {
                        itemsTag.setEnd(attributes.getValue(i));
                    }
                }
                setCustomItemsTagValue(itemsTag, attributes);
                itemsTags.add(itemsTag);
            } else if (TagType.DELETE.equals(qName)) {
                DeleteTag tag = new DeleteTag();
                deleteTags.add(tag);
            }
        }
    }

    protected abstract ItemsTag instanceItemsTag();

    protected abstract void setCustomRootTagValue(RootTag rootTag, Attributes attributes);

    protected abstract void setCustomItemsTagValue(ItemsTag itemsTag, Attributes attributes);
}