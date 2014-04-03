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

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Text;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject.NameRuleString;

public class PackageNameModifyListener implements ModifyListener {

    private ControlDecoration decorator;

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyText(ModifyEvent e) {
        Text text = (Text) e.widget;

        if (decorator == null) {
            decorator = new ControlDecoration(text, SWT.LEFT);
            decorator.setMarginWidth(4);
        }

        if (!new NameRuleString(text.getText()).isJavaPackageName()) {
            Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
            decorator.setImage(image);
            decorator.showHoverText("Not a valid Package Name");
        } else {
            decorator.hide();
            decorator.dispose();
            decorator = null;
        }
    }

    public boolean isVisible() {
        if (decorator != null && decorator.isVisible()) {
            return true;
        }
        return false;
    }

    public String getErrorMessage() {
        if (isVisible()) {
            Text text = (Text) decorator.getControl();
            return text.getText();
        }

        return "";
    }
}
