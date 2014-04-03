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
package com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.dialog;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public final class AsyncMessageDialog {

    private AsyncMessageDialog() {
    }

    public static void openError(final Shell shell, final String title, final String msg) {
        shell.getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                MessageDialog.openError(shell, title, msg);
            }
        });
    }

    public static void openError(final Shell shell, final String title, final String msg, final IStatus status) {
        shell.getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                ErrorDialog.openError(shell, title, msg, status);
            }
        });
    }

    public static void openInformation(final Shell shell, final String title, final String msg) {
        shell.getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                MessageDialog.openInformation(shell, title, msg);
            }
        });
    }

    public static void openWarning(final Shell shell, final String title, final String msg) {
        shell.getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                MessageDialog.openWarning(shell, title, msg);
            }
        });
    }

    public static void openConfirm(final Shell shell, final String title, final String msg) {
        shell.getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                MessageDialog.openConfirm(shell, title, msg);
            }
        });
    }
}
