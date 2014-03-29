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
package com.github.ko2ic.plugin.eclipse.taggen.core.service.dto;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.DeleteTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.TemplateCode;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.parse.TagExtracter;

/**
 * Holds tag object.
 * @author ko2ic
 */
@Getter
public class TagHolder {

    public final RootTag rootTag;

    public final List<DeleteTag> deleteTags;

    public final List<? extends ItemsTag> itemsTags;

    public final String beforeRootTagContents;

    public final String affterRootTagContents;

    public TagHolder(TemplateCode code, TagExtracter tagExtracter) {

        this.rootTag = tagExtracter.getRootTag();
        this.deleteTags = tagExtracter.getDeleteTags();
        this.itemsTags = tagExtracter.getItemsWithContents(code.getAllLines());

        this.beforeRootTagContents = code.getCodeBeforeRootTag(rootTag.getStartLineNumber());
        this.affterRootTagContents = code.getCodeAfterRootTag(rootTag.getEndLineNumber());
    }

    public List<DeleteTag> getDeleteTags() {
        return Collections.unmodifiableList(deleteTags);
    }

    public List<? extends ItemsTag> getItemsTags() {
        return Collections.unmodifiableList(itemsTags);
    }

}
