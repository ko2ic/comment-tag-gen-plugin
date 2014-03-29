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
package com.github.ko2ic.plugin.eclipse.taggen.core.service;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElements;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.GeneratingCodeSeedBase;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.spreadsheet.Workbook;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.google.common.base.Throwables;

public class SpreadSheetToBeanConverter {

    private Map<String, ? extends ClassElements> map;

    public SpreadSheetToBeanConverter(IProgressMonitor monitor, IFile spreadSheetFile, GeneratingCodeSeedBase codeSeed) {
        try {
            Workbook wb = new Workbook(spreadSheetFile.getContents());

            int sheetCounts = wb.getNumberOfSheets();
            monitor.beginTask("reading spreadsheet", sheetCounts);

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            for (Sheet sheet : wb) {
                codeSeed.grow(sheet);
                monitor.worked(1);
                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }
            this.map = codeSeed.harvest();
        } catch (CoreException e) {
            throw Throwables.propagate(new SystemException(e));
        } finally {
            monitor.done();
        }
    }

    public Map<String, ? extends ClassElements> getResult() {
        return Collections.unmodifiableMap(map);
    }
}
