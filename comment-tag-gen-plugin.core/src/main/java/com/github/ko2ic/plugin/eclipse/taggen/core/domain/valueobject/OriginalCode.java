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
package com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.google.common.base.Throwables;

/**
 * Presents Java code which user customize in project.
 * @author ko2ic
 */
public class OriginalCode {

    private final StringBuilder sb = new StringBuilder();

    public OriginalCode(IFile file) {
        File pFile = file.getRawLocation().toFile();
        if (!pFile.exists()) {
            return;
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(pFile), file.getCharset());) {
            int c;
            while (0 <= (c = reader.read())) {
                sb.append((char) c);
            }
        } catch (IOException | CoreException e) {
            throw Throwables.propagate(new SystemException(e));
        }
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
