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

import java.util.Iterator;

import lombok.Getter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;

/**
 * Presents a Sheet.
 * @author ko2ic
 */
public class SheetImpl implements Sheet {

    private final org.apache.poi.ss.usermodel.Sheet sheet;

    private RowIterator iterator;

    /**
     * constructor.
     * @param sheet a sheet
     */
    public SheetImpl(org.apache.poi.ss.usermodel.Sheet sheet) {
        this.sheet = sheet;
        iterator = new RowIterator(sheet);
    }

    @Override
    public String getSheetName() {
        return sheet.getSheetName();
    }

    /**
     * set index of row that begin repeating.
     * @param startIndex specifies non-empty row
     */
    @Override
    public void setStartRowIndex(int startIndex) {
        for (int i = 0; i < startIndex; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Row> iterator() {
        if (iterator == null) {
            iterator = new RowIterator(sheet);
        }
        return iterator;
    }

    /**
     * Gets a current row.
     * @return
     */
    @Override
    public Row getCurrentRow() {
        return iterator.getCurrentRow();
    }

    /**
     * Whether iterator had a empty row in the middle of iterating.
     * @return if true, row was empty.
     */
    @Override
    public boolean brokenSerial() {
        return iterator.brokenSerial();
    }

    /**
     * Recovers row number as a serial number.<br/>
     * After call {@link SheetImpl#brokenSerial()},you should call it.
     */
    @Override
    public void recoverBrokenSerial() {
        iterator.recoverBrokenSerial();
    }

    /**
     * Get the value of the cell as a string.<br/>
     * @param rowIndex a row of a spreadsheet
     * @param columnIndex 0-based column number
     * @return value of the cell as a string
     */
    @Override
    public String getStringCellValue(int rowIndex, int columnIndex) {
        Cell cell = getCell(rowIndex, columnIndex);
        return getStringCellValue(cell);
    }

    /**
     * Get the value of the cell as a string in current row.<br/>
     * @param columnIndex 0-based column number
     * @return a cell value
     */
    @Override
    public String getStringCellValue(int columnIndex) {
        Cell cell = getCurrentRow().getCell(columnIndex);
        return getStringCellValue(cell);
    }

    /**
     * Get the value of the cell as a string .
     * @param cell a cell in spreadsheet
     * @return
     */
    private String getStringCellValue(Cell cell) {
        int type = cell.getCellType();
        String result = null;
        if (type == Cell.CELL_TYPE_STRING) {
            result = cell.getStringCellValue();
        } else if (type == Cell.CELL_TYPE_NUMERIC) {
            result = String.valueOf((int) cell.getNumericCellValue());
        }
        return result;
    }

    private Cell getCell(int rowIndex, int columnIndex) {
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(columnIndex);
        return cell;
    }

    /**
     * Iterates row.
     * @author kouji ishii
     */
    private static class RowIterator implements Iterator<Row> {

        private final Iterator<Row> iterator;

        @Getter
        private Row currentRow;

        /** The row index in front of one */
        private int preRowNum = -1;

        public RowIterator(org.apache.poi.ss.usermodel.Sheet sheet) {
            this.iterator = sheet.iterator();
        }

        public boolean brokenSerial() {
            return currentRow.getRowNum() != ++preRowNum;
        }

        public void recoverBrokenSerial() {
            preRowNum = currentRow.getRowNum();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Row next() {
            currentRow = iterator.next();
            return currentRow;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            iterator.remove();
        }
    }

}
