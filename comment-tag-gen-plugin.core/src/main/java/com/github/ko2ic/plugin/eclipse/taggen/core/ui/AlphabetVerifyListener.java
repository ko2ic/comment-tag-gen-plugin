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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class AlphabetVerifyListener implements VerifyListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyText(VerifyEvent e) {
        switch (e.keyCode) {
        case SWT.BS:
        case SWT.DEL:
        case SWT.HOME:
        case SWT.END:
        case SWT.ARROW_LEFT:
        case SWT.ARROW_RIGHT:
            return;
        }

        if (!Character.isLetter(e.character)) {
            e.doit = false;
        }
    }

}
