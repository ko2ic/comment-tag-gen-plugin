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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.enums.TagType;

@AllArgsConstructor
public class CodeLine {

    @Getter
    private final int lineNumber;

    @Getter(lombok.AccessLevel.PROTECTED)
    private final String line;

    @Override
    public String toString() {
        return line;
    }

    public boolean isCommentTagLine() {
        return isDeleteTagLine() || isItemsTagLine() || isRootTagLine();
    }

    public boolean isRootTagLine() {
        return isRootTagEndLine() || isRootTagStartLine();
    }

    public boolean isItemsTagLine() {
        return isItemsTagEndLine() || isItemsTagStartLine();
    }

    public boolean isDeleteTagLine() {
        return isDeleteTagEndLine() || isDeleteTagStartLine();
    }

    public CodeLine replace(String bind, String replace) {
        String line = getLine().replace(bind, replace);
        return new CodeLine(getLineNumber(), line);
    }

    public boolean isRootTagStartLine() {
        return isTagStartLine(TagType.ROOT);
    }

    public boolean isRootTagEndLine() {
        return isTagEndLine(TagType.ROOT);
    }

    public boolean isItemsTagStartLine() {
        return isTagStartLine(TagType.ITEMS);
    }

    public boolean isItemsTagEndLine() {
        return isTagEndLine(TagType.ITEMS);
    }

    public boolean isDeleteTagStartLine() {
        return isTagStartLine(TagType.DELETE);
    }

    public boolean isDeleteTagEndLine() {
        return isTagEndLine(TagType.DELETE);
    }

    protected boolean isTagStartLine(TagType type) {
        return isTagLine(StartOrEndType.START, type);
    }

    protected boolean isTagEndLine(TagType type) {
        return isTagLine(StartOrEndType.END, type);
    }

    private boolean isTagLine(StartOrEndType flag, TagType type) {
        String str = String.format("\\s*<\\s*%s%s.*>\\s*", flag.getValue(), type.name());
        Pattern pattern = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line.replace("//", ""));
        return matcher.find();
    }

    private enum StartOrEndType {
        START(""), END("/");

        private final String value;

        private StartOrEndType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
