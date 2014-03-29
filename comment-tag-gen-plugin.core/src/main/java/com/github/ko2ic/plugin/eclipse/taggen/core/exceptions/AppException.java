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

/**
 * Check Exception<br>
 * @author ko2ic
 */
public abstract class AppException extends Exception {

    public AppException() {
        super();
    }

    public AppException(Throwable t) {
        super(t);
    }

    public abstract String getErrorMessage();
}
