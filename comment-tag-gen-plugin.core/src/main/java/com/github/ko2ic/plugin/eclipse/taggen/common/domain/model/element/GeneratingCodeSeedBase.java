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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;

/**
 * Handles a class information to generate code.
 * @author ko2ic
 */
public abstract class GeneratingCodeSeedBase {

    private final Map<String, ClassElements> map = new HashMap<>();

    private final boolean whetherPackageNameUsesSheet;

    public GeneratingCodeSeedBase(boolean whetherPackageNameUsesSheet) {
        this.whetherPackageNameUsesSheet = whetherPackageNameUsesSheet;
    }

    /**
     * Makes a class information used when generating code.
     * @param sheet sheet
     */
    public void grow(Sheet sheet) {

        String packageName = null;

        if (whetherPackageNameUsesSheet) {
            packageName = sheet.getSheetName();
        } else {
            packageName = getPackageName(sheet);
        }

        String fullClassName = null;
        sheet.setStartRowIndex(getStartIndexRepeatingRow());

        sheet.getCurrentRow();
        sheet.recoverBrokenSerial();
        fullClassName = putClassElements(sheet, packageName);

        for (@SuppressWarnings("unused")
        Row row : sheet) {
            if (sheet.brokenSerial()) {
                sheet.recoverBrokenSerial();
                fullClassName = putClassElements(sheet, packageName);
            } else {
                if (containsKey(fullClassName)) {
                    ClassElements seed = get(fullClassName);
                    ClassElementsItem type = instanceClassElementsItem(sheet);
                    seed.addClassElementsItem(type);
                } else {
                    fullClassName = putClassElements(sheet, packageName);
                }
            }
        }
    }

    protected abstract ClassElementsItem instanceClassElementsItem(Sheet sheet);

    /**
     * Puts seed that holds a class information.
     * @param sheet
     * @param packageName
     * @return package name + class name that key of map.
     */
    protected abstract String putClassElements(Sheet sheet, String packageName);

    /**
     * Gets row to start repeating (specifies non-empty row) .
     * @return non-empty row index
     */
    protected abstract int getStartIndexRepeatingRow();

    protected abstract String getPackageName(Sheet sheet);

    public Map<String, ClassElements> harvest() {
        return Collections.unmodifiableMap(map);
    }

    protected boolean containsKey(String key) {
        return map.containsKey(key);
    }

    protected ClassElements get(String key) {
        return map.get(key);
    }

    protected void put(String className, ClassElements elements) {
        map.put(className, elements);
    }
}
