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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElements;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.GeneratingCodeSeedBase;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject.NameRuleString;

/**
 * Holds some classes information used when generating code.
 * @author ko2ic
 */
public class EnumCodeSeed extends GeneratingCodeSeedBase {

    /**
     * constructor.
     * @param whetherPackageNameUsesSheet
     */
    public EnumCodeSeed(boolean whetherPackageNameUsesSheet) {
        super(whetherPackageNameUsesSheet);
    }

    /** B Column of spreadsheet */
    private static final int CELL_INDEX_CLASS_COMMENT = 1;

    /** C Column of spreadsheet */
    private static final int CELL_INDEX_CLASS_NAME = 2;

    /** D Column of spreadsheet */
    private static final int CELL_INDEX_ENUM_NAME = 3;

    /** E Column of spreadsheet */
    private static final int CELL_INDEX_ENUM_VALUE = 4;

    /** F Column of spreadsheet */
    private static final int CELL_INDEX_ENUM_COMMENT = 5;

    @Override
    protected String putClassElements(Sheet sheet, String packageName) {
        String definedClassName = sheet.getStringCellValue(CELL_INDEX_CLASS_NAME);

        NameRuleString javaClassName = new NameRuleString(definedClassName);

        String fullClassName = String.format("%s.%s", packageName, javaClassName.phraseClassName());
        EnumElements seed = createEnumElements(sheet, packageName, javaClassName.phraseClassName());
        put(fullClassName, seed);
        return fullClassName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ClassElementsItem instanceClassElementsItem(Sheet sheet) {
        return new EnumClassElementsItem(new NameRuleString(sheet.getStringCellValue(CELL_INDEX_ENUM_NAME)), sheet.getStringCellValue(CELL_INDEX_ENUM_VALUE),
                sheet.getStringCellValue(CELL_INDEX_ENUM_COMMENT));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getStartIndexRepeatingRow() {
        return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPackageName(Sheet sheet) {
        // get value of B1 cell in spreadsheet
        return sheet.getStringCellValue(0, 1);
    }

    /**
     * Creates EnumElements.
     * @param sheet a sheet
     * @param packageName
     * @param className the name used when generating
     * @return EnumElements
     */
    private EnumElements createEnumElements(Sheet sheet, String packageName, String className) {
        String classComment = sheet.getStringCellValue(CELL_INDEX_CLASS_COMMENT);
        EnumElements elements = new EnumElements(packageName, classComment, className);
        EnumClassElementsItem type = new EnumClassElementsItem(new NameRuleString(sheet.getStringCellValue(CELL_INDEX_ENUM_NAME)), sheet.getStringCellValue(CELL_INDEX_ENUM_VALUE),
                sheet.getStringCellValue(CELL_INDEX_ENUM_COMMENT));
        elements.addClassElementsItem(type);
        return elements;
    }

    /**
     * Holds enumeration elements.<br/>
     * @author Kouji Ishii
     */
    @RequiredArgsConstructor
    @Getter
    public class EnumElements implements ClassElements {

        private final String packageName;

        /** javadoc comment for class */
        private final String classComment;

        /** class name */
        private final String className;

        /** enum type list */
        private final List<EnumClassElementsItem> classElementsItems = new ArrayList<>();

        /**
         * Adds EnumAttribute.<br/>
         * @param type type
         */
        @Override
        public void addClassElementsItem(ClassElementsItem type) {
            classElementsItems.add((EnumClassElementsItem) type);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<? extends ClassElementsItem> getClassElementsItem() {
            return classElementsItems;
        }
    }

    /**
     * Presents Enum element.<br/>
     * @author Kouji Ishii
     */
    @RequiredArgsConstructor
    @Getter
    public static class EnumClassElementsItem implements ClassElementsItem {

        /** enum name */
        private final NameRuleString name;

        /** enum value */
        private final String code;

        /** javdoc comment for the enum */
        private final String comment;

        public String getUpperCamelName() {
            return name.toUpperCamel();
        }

        public String getUpperSnakeName() {
            return name.phraseConstantName();
        }

        public String getUpperName() {
            return name.toUpperCase();
        }
    }
}
