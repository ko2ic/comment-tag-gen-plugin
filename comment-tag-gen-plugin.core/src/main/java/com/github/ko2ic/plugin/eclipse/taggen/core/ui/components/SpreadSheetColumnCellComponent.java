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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.github.ko2ic.plugin.eclipse.taggen.core.ui.AlphabetVerifyListener;

public class SpreadSheetColumnCellComponent {

    private final Text text;

    private final AlphabetVerifyListener listener = new AlphabetVerifyListener();

    public SpreadSheetColumnCellComponent(Composite parent, String labelText) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(labelText);
        text = new Text(parent, SWT.SINGLE | SWT.BORDER);
        text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    public void setText(String value) {
        text.setText(value);
    }

    public String getText() {
        return text.getText();
    }

    public void setEnabled(boolean b) {
        text.setEnabled(b);
    }

    public void verify() {
        text.addVerifyListener(listener);
    }

    public void dispose() {
        text.removeVerifyListener(listener);
    }
}
