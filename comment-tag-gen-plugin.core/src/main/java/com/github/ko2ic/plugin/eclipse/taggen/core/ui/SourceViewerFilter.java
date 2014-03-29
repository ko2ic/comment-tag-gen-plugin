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
package com.github.ko2ic.plugin.eclipse.taggen.core.ui;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

@SuppressWarnings("restriction")
public class SourceViewerFilter extends ViewerFilter {

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element instanceof IJavaModel) {
            return true;
        } else if (element instanceof JarPackageFragmentRoot) {
            return false;
        } else if (element instanceof IJavaProject) {
            return true;
        } else if (element instanceof PackageFragmentRoot) {
            return true;
        } else if (element instanceof PackageFragment) {
            PackageFragment packageFragment = (PackageFragment) element;
            if (packageFragment.isDefaultPackage()) {
                return false;
            }
            return true;
        } else if (element instanceof ICompilationUnit) {
            return true;
        }
        return false;
    }
}
