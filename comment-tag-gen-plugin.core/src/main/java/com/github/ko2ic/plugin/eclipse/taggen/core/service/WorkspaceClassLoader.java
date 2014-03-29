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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.google.common.base.Throwables;

public class WorkspaceClassLoader {

    private URLClassLoader classLoader;

    public WorkspaceClassLoader() {
        try {
            List<IJavaProject> javaProjects = createJavaProjects();
            Set<URL> urlList = new HashSet<>();
            for (IJavaProject javaProject : javaProjects) {
                urlList.addAll(createUrls(javaProject));
            }
            URL[] urls = urlList.toArray(new URL[urlList.size()]);
            classLoader = new URLClassLoader(urls, getClass().getClassLoader());
        } catch (CoreException | MalformedURLException e) {
            throw Throwables.propagate(new SystemException(e));
        }
    }

    private List<IJavaProject> createJavaProjects() throws CoreException {
        List<IJavaProject> javaProjects = new ArrayList<IJavaProject>();
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for (IProject project : projects) {
            project.open(null /* IProgressMonitor */);
            IJavaProject javaProject = JavaCore.create(project);
            javaProjects.add(javaProject);
        }
        return javaProjects;
    }

    private Set<URL> createUrls(IJavaProject javaProject) throws CoreException, MalformedURLException {
        Set<URL> urlList = new HashSet<>();
        IClasspathEntry[] classpathEntries = javaProject.getResolvedClasspath(true);
        for (IClasspathEntry entry : classpathEntries) {
            if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
                IPath path = entry.getOutputLocation();
                if (path != null) {
                    IPath outputPath = javaProject.getOutputLocation().removeFirstSegments(1);
                    IPath outputFullPath = javaProject.getProject().getFolder(outputPath).getLocation();
                    outputFullPath.append(System.getProperty("line.separator"));
                    URL url = outputFullPath.toFile().toURI().toURL();
                    urlList.add(url);
                }
            } else if (entry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
                URL url = entry.getPath().toFile().toURI().toURL();
                urlList.add(url);
            }
        }
        return urlList;
    }

    public Class<?> loadClass(String fullyQualifiedName) {
        try {
            return classLoader.loadClass(fullyQualifiedName);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

}
