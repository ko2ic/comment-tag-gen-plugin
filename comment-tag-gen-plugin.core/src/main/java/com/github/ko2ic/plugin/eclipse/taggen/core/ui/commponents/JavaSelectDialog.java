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
package com.github.ko2ic.plugin.eclipse.taggen.core.ui.commponents;

import lombok.Getter;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.github.ko2ic.plugin.eclipse.taggen.core.ui.SourceViewerFilter;

public class JavaSelectDialog extends TitleAreaDialog {

    @Getter
    private ICompilationUnit compilationUnit;

    private final String message;

    private TreeViewer tree;

    public JavaSelectDialog(Shell parentShell, String message) {
        super(parentShell);
        setDialogHelpAvailable(false);
        setHelpAvailable(false);
        this.message = message;
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
    }

    @Override
    protected Point getInitialSize() {
        return new Point(500, 400);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        setTitle("Choose a Class");
        setMessage(message, IMessageProvider.NONE);
        Control control = super.createDialogArea(parent);
        Composite composite = new Composite((Composite) control, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        FillLayout layout = new FillLayout();
        composite.setLayout(layout);
        tree = new TreeViewer(composite, SWT.BORDER);
        tree.setContentProvider(new StandardJavaElementContentProvider());
        tree.addFilter(new SourceViewerFilter());
        JavaElementLabelProvider provider = new JavaElementLabelProvider();
        tree.setLabelProvider(provider);
        IJavaModel javaModel = JavaCore.create(ResourcesPlugin.getWorkspace().getRoot());
        tree.setInput(javaModel);
        tree.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                selectionChangeHandler(event);
            }
        });
        return control;
    }

    @Override
    protected Control createContents(Composite parent) {
        Control control = super.createContents(parent);
        getButton(IDialogConstants.OK_ID).setEnabled(false);
        return control;
    }

    private void selectionChangeHandler(SelectionChangedEvent event) {
        Object obj = ((StructuredSelection) event.getSelection()).getFirstElement();
        if (obj instanceof ICompilationUnit) {
            compilationUnit = (ICompilationUnit) obj;
            getButton(IDialogConstants.OK_ID).setEnabled(true);
        } else {
            compilationUnit = null;
            getButton(IDialogConstants.OK_ID).setEnabled(false);
        }

    }
}
