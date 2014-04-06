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
package com.github.ko2ic.plugin.eclipse.taggen.core.exceptions;

import lombok.RequiredArgsConstructor;

import com.github.ko2ic.plugin.eclipse.taggen.common.exceptions.AppException;

@RequiredArgsConstructor
public class AppNotImplementsException extends AppException {

    private final String packageName;

    private final String mustImplements;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessage() {
        return String.format("\"%s\" as a custom code must implement \"%s\".", packageName, mustImplements);
    }
}
