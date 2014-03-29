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

import lombok.Data;

import com.google.common.base.Throwables;

/**
 * Presents Base of all Tags.<br>
 * @author ko2ic
 */
@Data
public abstract class CommentTag implements Cloneable {

    private int startLineNumber;

    private int endLineNumber;

    @Override
    public CommentTag clone() {
        try {
            CommentTag tag = (CommentTag) super.clone();
            tag.setStartLineNumber(getStartLineNumber());
            tag.setEndLineNumber(getEndLineNumber());
            return tag;
        } catch (CloneNotSupportedException e) {
            throw Throwables.propagate(e);
        }
    }

    public enum TagType {
        ROOT, ITEMS, DELETE;

        public boolean equals(String tagName) {
            return this.name().equalsIgnoreCase(tagName);
        }
    }
}
