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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElements;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.CommentTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.DeleteTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;
import com.github.ko2ic.plugin.eclipse.taggen.core.Activator;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.AppFileNotFoundException;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.google.common.base.Throwables;

/**
 * Presents template code.
 * @author ko2ic
 */
public class TemplateCode {

    /** all code lines */
    private final Lines lines = new Lines();

    /**
     * Reads and Holds contents of code.<br>
     * @param is stream of template code
     */
    public TemplateCode(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
            for (String str = br.readLine(); str != null; str = br.readLine()) {
                lines.add(str);
            }
        } catch (IOException e) {
            throw Throwables.propagate(new SystemException(e));
        }
    }

    public List<CodeLine> getAllLines() {
        return lines.getAllLines();
    }

    public List<CodeLine> getTagLines() {
        return lines.getTagLines();
    }

    @Override
    public String toString() {
        return lines.toString();
    }

    public int getLineCount() {
        return lines.size();
    }

    public String getCodeInsideRootTag(ClassElements elements, RootTag rootTag, List<? extends ItemsTag> list, List<DeleteTag> deleteTags) {

        StringBuilder sb = new StringBuilder();
        for (int lineNumber = 1; lineNumber <= lines.size(); lineNumber++) {
            CodeLine line = lines.get(lineNumber);
            if (lines.inTag(deleteTags, lineNumber) || line.isDeleteTagLine()) {
                continue;
            }

            if (lines.inTag(rootTag, lineNumber)) {
                line = line.replace(rootTag.getClassName(), elements.getClassName());
                line = line.replace(rootTag.getClassComment(), elements.getClassComment());

                boolean wroteItemsTag = false;
                for (ItemsTag itemsTag : list) {
                    if (itemsTag.getEndLineNumber() == lineNumber) {
                        sb.append(itemsTag.getContents());
                        wroteItemsTag = true;
                        break;
                    }
                }

                if (wroteItemsTag) {
                    continue;
                }

                if (!lines.inTag(list, lineNumber) && !line.isItemsTagLine()) {
                    sb.append(line);
                }
            }
        }
        return sb.toString();
    }

    public String getCodeBeforeRootTag(final int startLine) {
        return getCodeArroundRootTag(null, new Predicate() {

            @Override
            public boolean evaluate(int lineNumber) {
                return startLine > lineNumber;
            }
        });
    }

    public String getCodeAfterRootTag(final int endLine) {
        return getCodeArroundRootTag(null, new Predicate() {

            @Override
            public boolean evaluate(int lineNumber) {
                return endLine < lineNumber;
            }
        });
    }

    private String getCodeArroundRootTag(List<CommentTag> deleteTags, Predicate predicate) {
        StringBuilder sb = new StringBuilder();
        for (int lineNumber = 1; lineNumber <= lines.size(); lineNumber++) {
            if (isSkip(deleteTags, lineNumber)) {
                continue;
            }

            if (predicate.evaluate(lineNumber)) {
                sb.append(lines.get(lineNumber));
            }
        }
        return sb.toString();
    }

    /**
     * whether inside delete tag.<br>
     * @param deleteTags list of ¥<delete¥>
     * @param lineNumber lineNumber
     * @return if true,skip
     */
    private boolean isSkip(List<CommentTag> deleteTags, int lineNumber) {
        boolean skip = false;
        if (deleteTags != null) {
            for (CommentTag deleteTagInfo : deleteTags) {
                if (deleteTagInfo.getStartLineNumber() <= lineNumber && deleteTagInfo.getEndLineNumber() >= lineNumber) {
                    skip = true;
                }
            }
        }
        return skip;
    }

    private interface Predicate {
        boolean evaluate(int lineNumber);
    }

    public static String getDefaultTemplateCodePath() throws AppFileNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("templates").append(File.separator);
        sb.append("Flag.java");
        try {
            URL templateURL = Activator.getDefault().getBundle().getEntry(sb.toString());
            return FileLocator.toFileURL(templateURL).getFile();
        } catch (Exception e) {
            throw new AppFileNotFoundException(sb.toString());
        }
    }
}
