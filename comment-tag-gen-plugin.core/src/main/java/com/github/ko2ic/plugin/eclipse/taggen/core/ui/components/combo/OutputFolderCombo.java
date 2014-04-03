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
package com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.combo;

import java.util.HashSet;
import java.util.Set;

import lombok.Delegate;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.google.common.base.Throwables;

public class OutputFolderCombo {

    @Delegate
    private final Combo outputFolderCombo;

    private final Set<String> outputFolders = new HashSet<>();

    public OutputFolderCombo(Composite parent, int style) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject[] projects = root.getProjects();

        for (IProject project : projects) {
            try {
                IJavaProject javaProject = JavaCore.create(project);
                IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots();
                for (IPackageFragmentRoot pRoot : roots) {
                    if (pRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
                        outputFolders.add(pRoot.getPath().removeFirstSegments(1).toOSString());
                    }
                }
            } catch (JavaModelException e) {
                throw Throwables.propagate(new SystemException(e));
            }
        }

        outputFolderCombo = new Combo(parent, style);
    }

    public void select(String value) {
        int outputFoldersIndex = 0;
        for (String outputFolder : outputFolders) {
            outputFolderCombo.add(outputFolder);
            if (outputFolder.equals(value)) {
                outputFolderCombo.select(outputFoldersIndex);
            }
            outputFoldersIndex++;
        }
        if (outputFolderCombo.getSelectionIndex() == -1) {
            outputFolderCombo.select(0);
        }
    }
}
