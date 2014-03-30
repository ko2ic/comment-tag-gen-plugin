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

//@RequiredArgsConstructor
public class AppFileNotFoundException extends AppException {

    private final String fileName;

    public AppFileNotFoundException(String fileName) {
        this.fileName = fileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessage() {
        return String.format("\"%s\" as a template file is not found.", fileName);
    }
}
