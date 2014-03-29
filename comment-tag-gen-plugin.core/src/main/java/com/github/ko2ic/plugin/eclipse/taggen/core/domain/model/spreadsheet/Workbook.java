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
package com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.spreadsheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;
import com.github.ko2ic.plugin.eclipse.taggen.core.Activator;

/**
 * Presents SpreadSheet.
 * @author ko2ic
 */
public class Workbook implements Iterable<Sheet> {

    private final ILog log = Activator.getDefault().getLog();

    private org.apache.poi.ss.usermodel.Workbook workbook;

    public Workbook(InputStream is) {
        try {
            workbook = WorkbookFactory.create(is);
        } catch (InvalidFormatException | IOException e) {
            log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage()));
        }
    }

    public int getNumberOfSheets() {
        return workbook.getNumberOfSheets();
    }

    @Override
    public Iterator<Sheet> iterator() {
        return new SheetIterator(workbook);
    }

    private class SheetIterator implements Iterator<Sheet> {
        private int now = 0;

        private final org.apache.poi.ss.usermodel.Workbook workbook;

        private final int sheetSize;

        public SheetIterator(org.apache.poi.ss.usermodel.Workbook workbook) {
            this.workbook = workbook;
            sheetSize = workbook.getNumberOfSheets();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return now < sheetSize;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Sheet next() {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(now);
            now++;
            return new SheetImpl(sheet);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            workbook.removeSheetAt(now);
            now--;
        }

    }
}
