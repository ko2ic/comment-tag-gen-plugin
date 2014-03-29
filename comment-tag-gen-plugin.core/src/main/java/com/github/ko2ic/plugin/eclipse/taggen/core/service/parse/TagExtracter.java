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
package com.github.ko2ic.plugin.eclipse.taggen.core.service.parse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.CommentTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.DeleteTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.service.parse.TagHandlerBase;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.CodeLine;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.google.common.base.Throwables;

/**
 * Base Class that extracts a tag information from template code.
 * @author ko2ic
 */
public class TagExtracter {

    private final RootTag rootTag;

    private final List<ItemsTag> itemsTags = new ArrayList<>();

    private final List<DeleteTag> deleteTags = new ArrayList<>();

    public RootTag getRootTag() {
        return rootTag;
    }

    public List<? extends ItemsTag> getItemsTags() {
        return itemsTags;
    }

    public List<DeleteTag> getDeleteTags() {
        return deleteTags;
    }

    public TagExtracter(List<CodeLine> tagLines, TagClosureCreator<? extends ItemsTag> itemsTagClosureCreator, TagHandlerBase tagHandler) {
        this.rootTag = tagHandler.getRootTag();
        StringBuilder sb = new StringBuilder();
        TagClosureCreator.TagClosure<? extends ItemsTag> itemsHolder = itemsTagClosureCreator.getHolder();
        TagClosureCreator.TagClosure<DeleteTag> deleteHolder = new DeleteTagClosureCreator().getHolder();

        for (CodeLine line : tagLines) {
            sb.append(line.toString());
            if (line.isRootTagStartLine()) {
                rootTag.setStartLineNumber(line.getLineNumber());
            } else if (line.isRootTagEndLine()) {
                rootTag.setEndLineNumber(line.getLineNumber());
            } else if (line.isItemsTagStartLine()) {
                itemsHolder.getValue().setStartLineNumber(line.getLineNumber());
            } else if (line.isItemsTagEndLine()) {
                itemsHolder.getValue().setEndLineNumber(line.getLineNumber());
                itemsTags.add(itemsHolder.getValue().clone());
                itemsHolder.clear();
            } else if (line.isDeleteTagStartLine()) {
                deleteHolder.getValue().setStartLineNumber(line.getLineNumber());
            } else if (line.isDeleteTagEndLine()) {
                deleteHolder.getValue().setEndLineNumber(line.getLineNumber());
                deleteTags.add(deleteHolder.getValue().clone());
                deleteHolder.clear();
            }
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
            parser.parse(is, tagHandler);

            List<? extends ItemsTag> itemsTags = tagHandler.getItemsTag();
            for (int i = 0; i < itemsTags.size(); i++) {
                ItemsTag srcTag = itemsTags.get(i);
                ItemsTag destTag = this.itemsTags.get(i);
                destTag.interchange(srcTag);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw Throwables.propagate(new SystemException(e));
        }
    }

    public List<? extends ItemsTag> getItemsWithContents(List<CodeLine> allLines) {

        for (ItemsTag itemsTag : itemsTags) {
            StringBuilder itemsTagContents = new StringBuilder();
            for (int lineNumber = itemsTag.getStartLineNumber() + 1; lineNumber <= itemsTag.getEndLineNumber() - 1; lineNumber++) {
                boolean inDelete = false;
                for (DeleteTag deleteTag : deleteTags) {
                    if (deleteTag.getStartLineNumber() <= lineNumber && deleteTag.getEndLineNumber() >= lineNumber) {
                        inDelete = true;
                        continue;
                    }
                }
                if (inDelete) {
                    continue;
                }
                itemsTagContents.append(allLines.get(lineNumber - 1));
            }
            itemsTag.setContents(itemsTagContents.toString());
        }

        return itemsTags;
    }

    protected static class DeleteTagClosureCreator extends TagClosureCreator<DeleteTag> {
        @Override
        protected DeleteTag instance() {
            return new DeleteTag();
        }
    }

    public static abstract class TagClosureCreator<T extends CommentTag> {

        protected abstract T instance();

        public TagClosure<T> getHolder() {
            final T value = instance();

            return new TagClosure<T>() {
                @Override
                public T getValue() {
                    return value;
                }

                @Override
                public void clear() {
                    value.setStartLineNumber(0);
                    value.setEndLineNumber(0);
                }
            };
        }

        public interface TagClosure<T extends CommentTag> {
            T getValue();

            void clear();
        }
    }

}
