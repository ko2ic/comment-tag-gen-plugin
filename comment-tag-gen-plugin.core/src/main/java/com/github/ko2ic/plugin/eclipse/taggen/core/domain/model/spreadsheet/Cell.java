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

public class Cell {

    public static String convertToColumnName(int columnIndex) {
        int base = 26;
        StringBuffer b = new StringBuffer();
        do {
            int digit = columnIndex % base + 65;
            b.append(Character.valueOf((char) digit));
            columnIndex = (columnIndex / base) - 1;
        } while (columnIndex >= 0);
        return b.reverse().toString();
    }

    public static int convertToColumnIndex(String columnName) {
        columnName = columnName.toUpperCase();
        int value = 0;
        for (int i = 0, k = columnName.length() - 1; i < columnName.length(); i++, k--) {
            int alpabetIndex = (columnName.charAt(i)) - 64;
            int delta = 0;
            if (k == 0) {
                delta = alpabetIndex - 1;
            } else {
                if (alpabetIndex == 0) {
                    delta = (26 * k);
                } else {
                    delta = (alpabetIndex * 26 * k);
                }
            }
            value += delta;
        }
        return value;
    }
}
