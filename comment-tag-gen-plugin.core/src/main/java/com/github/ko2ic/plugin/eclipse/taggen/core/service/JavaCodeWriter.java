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
package com.github.ko2ic.plugin.eclipse.taggen.core.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElements;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.OriginalCode;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.SelectObject;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.TemplateCode;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.dto.TagHolder;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.merge.EclipseJavaMerger;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.parse.TagExtracter;

public class JavaCodeWriter {

    private final IProgressMonitor monitor;

    private final EclipseJavaMerger merger = new EclipseJavaMerger();

    private final IPackageFragmentRoot pRoot;

    private final IFile spreadsheetFile;

    private final TagHolder tagHolder;

    private final TemplateCode templateCode;

    public JavaCodeWriter(IProgressMonitor monitor, SelectObject selection, TemplateCode code, TagExtracter tagExtracter) {
        this.monitor = monitor;
        this.pRoot = selection.getPackageRoot();
        this.spreadsheetFile = selection.getFile();
        this.tagHolder = new TagHolder(code, tagExtracter);
        this.templateCode = code;
    }

    public void write(Map<String, ? extends ClassElements> map) throws CoreException {

        try {
            monitor.beginTask("Code Generating", map.size());

            IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

            for (String key : map.keySet()) {

                ClassElements elements = map.get(key);
                for (ItemsTag itemsTag : tagHolder.getItemsTags()) {
                    itemsTag.replaceContent(elements.getClassElementsItem());
                }

                IPackageFragment packageFlagment = pRoot.createPackageFragment(elements.getPackageName(), true, null);

                String className = elements.getClassName();
                IPath packagePath = packageFlagment.getPath();
                IPath javaPath = packagePath.append(className + ".java");
                IFile javaFile = workspaceRoot.getFile(javaPath);

                OriginalCode originCode = new OriginalCode(javaFile);

                String newFirst = tagHolder.getBeforeRootTagContents().replaceFirst("templates", elements.getPackageName());
                String middle = templateCode.getCodeInsideRootTag(elements, tagHolder.getRootTag(), tagHolder.getItemsTags(), tagHolder.getDeleteTags());
                String newCode = merger.merge(newFirst + middle + tagHolder.getAffterRootTagContents(), originCode.toString());

                IFolder folder = (IFolder) spreadsheetFile.getParent();
                if (!folder.exists()) {
                    try {
                        folder.create(true, true, null);
                    } catch (CoreException e) {
                        throw e;
                    }
                }

                javaFile.delete(true, null);
                javaFile.create(new ByteArrayInputStream(newCode.getBytes()), true, null);
                monitor.worked(1);

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }
        } finally {
            monitor.done();
        }

    }

    public interface ItemsTagFunctor {
        public void replaceContent(ItemsTag itemsTag, List<? extends ClassElementsItem> attributes);
    }
}
