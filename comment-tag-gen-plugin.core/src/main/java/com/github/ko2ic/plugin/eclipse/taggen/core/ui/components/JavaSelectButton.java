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
package com.github.ko2ic.plugin.eclipse.taggen.core.ui.components;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.dialog.JavaSelectDialog;
import com.google.common.base.Throwables;

public class JavaSelectButton {

    private final Button button;

    public JavaSelectButton(Composite parent) {
        button = new Button(parent, SWT.PUSH);
        button.setText(JFaceResources.getString("openBrowse"));
    }

    public void addSelectionListener(SelectionListener listener) {
        button.addSelectionListener(listener);
    }

    static class JavaPathButtonListener implements SelectionListener {

        private final Text text;

        public JavaPathButtonListener(Text text) {
            this.text = text;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            JavaSelectDialog dialog = new JavaSelectDialog(new Shell(), "Please select a custom template.");
            dialog.open();
            ICompilationUnit unit = dialog.getCompilationUnit();
            text.setText(unit.getPath().toFile().getPath());
            dialog.close();
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }
    }

    static class JavaPackageButtonListener implements SelectionListener {
        private final Text text;

        public JavaPackageButtonListener(Text text) {
            this.text = text;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            Shell shell = Display.getDefault().getActiveShell();
            JavaSelectDialog dialog = new JavaSelectDialog(shell, "Please select a custom class.");
            dialog.open();
            ICompilationUnit unit = dialog.getCompilationUnit();
            String javaFileName = unit.getElementName();
            String javaName = javaFileName.replaceFirst(".java$", "");
            try {
                IPackageDeclaration declaration = unit.getPackageDeclarations()[0];
                String packageName = declaration.getElementName();
                String fullName = packageName + "." + javaName;
                text.setText(fullName);
            } catch (JavaModelException t) {
                throw Throwables.propagate(new SystemException(t));
            } finally {
                dialog.close();
            }
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }
    }
}
