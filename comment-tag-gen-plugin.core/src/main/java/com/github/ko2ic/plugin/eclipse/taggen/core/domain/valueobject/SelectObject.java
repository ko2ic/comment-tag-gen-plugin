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

import lombok.Getter;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.ILog;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.github.ko2ic.plugin.eclipse.taggen.core.Activator;

/**
 * Presents Object Selected by right-click.
 * @author ko2ic
 */
@Getter
public final class SelectObject {

    private final ILog log = Activator.getDefault().getLog();

    private IProject project;

    private IJavaProject javaProject;

    private IFile file;

    private IPackageFragmentRoot packageRoot;

    public SelectObject(ExecutionEvent event, String outputFolder) {
        ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (selection instanceof TreeSelection) {
            TreeSelection tselection = (TreeSelection) selection;
            TreePath[] paths = tselection.getPaths();
            TreePath path = paths[0];
            Object firstSegment = path.getFirstSegment();
            if (firstSegment instanceof IProject) {
                project = (IProject) path.getFirstSegment();
                javaProject = JavaCore.create(project);
            } else if (firstSegment instanceof IJavaProject) {
                javaProject = (IJavaProject) path.getFirstSegment();
                project = javaProject.getProject();
            }

            IFolder source = project.getFolder(outputFolder);
            IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(source);
            packageRoot = root;

            Object o = tselection.getFirstElement();
            if (o instanceof IFile) {
                this.file = (IFile) o;
            }
        }
    }
}
