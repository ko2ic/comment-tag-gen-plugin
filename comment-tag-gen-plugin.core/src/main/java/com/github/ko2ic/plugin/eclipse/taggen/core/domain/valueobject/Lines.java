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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.CommentTag;

/**
 * Represents all lines.<br/>
 * this class is almost immutable.{@link Lines#add(String)} method can change the state but package scope.
 * @author ko2ic
 */
public class Lines {

    private final List<CodeLine> allLine = new ArrayList<>();

    private final List<CodeLine> tagLines = new ArrayList<>();

    public List<CodeLine> getTagLines() {
        return Collections.unmodifiableList(tagLines);
    }

    void add(String str) {
        CodeLine line = new CodeLine(allLine.size() + 1, str + System.getProperty("line.separator"));
        if (line.isCommentTagLine()) {
            tagLines.add(line.replace("//", ""));
        }

        allLine.add(line);
    }

    public boolean inTag(CommentTag tag, Integer lineNumber) {
        return tag.getStartLineNumber() < lineNumber && tag.getEndLineNumber() > lineNumber;
    }

    public boolean inTag(List<? extends CommentTag> tags, Integer lineNumber) {
        for (CommentTag tag : tags) {
            if (inTag(tag, lineNumber)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return allLine.size();
    }

    public CodeLine get(Integer lineNumber) {
        return allLine.get(lineNumber - 1);
    }

    public List<CodeLine> getAllLines() {
        return Collections.unmodifiableList(allLine);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CodeLine line : allLine) {
            sb.append(line.toString());
        }
        return sb.toString();
    }

}
