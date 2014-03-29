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
package com.github.ko2ic.plugin.eclipse.taggen.core.persistense;

import org.eclipse.jface.preference.IPreferenceStore;

import com.github.ko2ic.plugin.eclipse.taggen.core.Activator;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.CustomCodePreference;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.SpreadsheetPreference;
import com.google.common.base.Strings;

public class PreferenceDao {

    private static final String OUTPUT_FOLDER_KEY = "OUTPUT_FOLDER_KEY";

    private static final String TEMPLATE_FILE_KEY = "TEMPLATE_FILE_KEY";

    private static final String CUSTOM_CODE_SEED_KEY = "CUSTOM_CODE_SEED_KEY";

    private static final String CUSTOM_ROOT_TAG_KEY = "CUSTOM_ROOT_TAG_KEY";

    private static final String CUSTOM_ITEMS_TAG_KEY = "CUSTOM_ITEMS_TAG_KEY";

    private static final String CUSTOM_TAG_HANDLER_KEY = "CUSTOM_TAG_HANDLER_KEY";

    private static final String SPREADSHEET_PACKAGE_USE_SHEET_KEY = "SPREADSHEET_PACKAGE_USE_SHEET_KEY";

    private static final String SPREADSHEET_PACKAGE_COLUMN_KEY = "SPREADSHEET_PACKAGE_COLUMN_KEY";

    private static final String SPREADSHEET_PACKAGE_ROW_KEY = "SPREADSHEET_PACKAGE_ROW_KEY";

    private static final String SPREADSHEET_CLASS_COMMENT_KEY = "SPREADSHEET_CLASS_COMMENT_KEY";

    private static final String SPREADSHEET_CLASS_NAME_KEY = "SPREADSHEET_CLASS_NAME_KEY";

    private static final String SPREADSHEET_ENUM_COMMENT_KEY = "SPREADSHEET_ENUM_COMMENT_KEY";

    private static final String SPREADSHEET_ENUM_NAME_KEY = "SPREADSHEET_ENUM_NAME_KEY";

    private static final String SPREADSHEET_ENUM_VALUE_KEY = "SPREADSHEET_ENUM_VALUE_KEY";

    private static final String SPREADSHEET_START_REPEAT_ROW_KEY = "SPREADSHEET_START_REPEAT_ROW_KEY";

    private final IPreferenceStore store = Activator.getDefault().getPreferenceStore();

    public String findOutputFolder() {
        return store.getString(OUTPUT_FOLDER_KEY) != null ? store.getString(OUTPUT_FOLDER_KEY) : "";
    }

    public CustomCodePreference findCustomCodePreference() {
        CustomCodePreference entity = new CustomCodePreference();
        entity.setCodeSeedImplements(store.getString(CUSTOM_CODE_SEED_KEY) != null ? store.getString(CUSTOM_CODE_SEED_KEY) : "");
        entity.setItemsTagImplements(store.getString(CUSTOM_ITEMS_TAG_KEY) != null ? store.getString(CUSTOM_ITEMS_TAG_KEY) : "");
        entity.setRootTagImplements(store.getString(CUSTOM_ROOT_TAG_KEY) != null ? store.getString(CUSTOM_ROOT_TAG_KEY) : "");
        entity.setTagHandlerImplements(store.getString(CUSTOM_TAG_HANDLER_KEY) != null ? store.getString(CUSTOM_TAG_HANDLER_KEY) : "");
        entity.setTemplateFilePath(store.getString(TEMPLATE_FILE_KEY) != null ? store.getString(TEMPLATE_FILE_KEY) : "");
        return entity;
    }

    public SpreadsheetPreference findSpreadsheetCellPreference() {
        SpreadsheetPreference entity = new SpreadsheetPreference();
        entity.setClassCommentCell(store.getString(SPREADSHEET_CLASS_COMMENT_KEY) != null ? store.getString(SPREADSHEET_CLASS_COMMENT_KEY) : "");
        entity.setClassNameCell(store.getString(SPREADSHEET_CLASS_NAME_KEY) != null ? store.getString(SPREADSHEET_CLASS_NAME_KEY) : "");
        entity.setEnumCommentCell(store.getString(SPREADSHEET_ENUM_COMMENT_KEY) != null ? store.getString(SPREADSHEET_ENUM_COMMENT_KEY) : "");
        entity.setEnumNameCell(store.getString(SPREADSHEET_ENUM_NAME_KEY) != null ? store.getString(SPREADSHEET_ENUM_NAME_KEY) : "");
        entity.setEnumValueCell(store.getString(SPREADSHEET_ENUM_VALUE_KEY) != null ? store.getString(SPREADSHEET_ENUM_VALUE_KEY) : "");
        entity.setPackageUseSheet(store.getBoolean(SPREADSHEET_PACKAGE_USE_SHEET_KEY));
        entity.setPackageColumnCell(store.getString(SPREADSHEET_PACKAGE_COLUMN_KEY) != null ? store.getString(SPREADSHEET_PACKAGE_COLUMN_KEY) : "");
        entity.setPackageRowCell(store.getString(SPREADSHEET_PACKAGE_ROW_KEY) != null ? store.getString(SPREADSHEET_PACKAGE_ROW_KEY) : "");

        String startIndex = store.getString(SPREADSHEET_START_REPEAT_ROW_KEY);
        if (!Strings.isNullOrEmpty(startIndex)) {
            entity.setStartRepeatRow(Integer.valueOf(startIndex));
        }
        return entity;
    }

    public void storeOutputFolder(String outputHolder) {
        store.setValue(OUTPUT_FOLDER_KEY, outputHolder);
    }

    public void storeCustomCode(CustomCodePreference entity) {
        store.setValue(TEMPLATE_FILE_KEY, entity.getTemplateFilePath());
        store.setValue(CUSTOM_CODE_SEED_KEY, entity.getCodeSeedImplements());
        store.setValue(CUSTOM_ROOT_TAG_KEY, entity.getRootTagImplements());
        store.setValue(CUSTOM_ITEMS_TAG_KEY, entity.getItemsTagImplements());
        store.setValue(CUSTOM_TAG_HANDLER_KEY, entity.getTagHandlerImplements());
    }

    public void storeSpreadsheetCell(SpreadsheetPreference entity) {
        store.setValue(SPREADSHEET_PACKAGE_USE_SHEET_KEY, entity.isPackageUseSheet());
        store.setValue(SPREADSHEET_PACKAGE_COLUMN_KEY, entity.getPackageColumnCell());
        store.setValue(SPREADSHEET_PACKAGE_ROW_KEY, entity.getPackageRowCell());
        store.setValue(SPREADSHEET_CLASS_COMMENT_KEY, entity.getClassCommentCell());
        store.setValue(SPREADSHEET_CLASS_NAME_KEY, entity.getClassNameCell());
        store.setValue(SPREADSHEET_ENUM_COMMENT_KEY, entity.getEnumCommentCell());
        store.setValue(SPREADSHEET_ENUM_NAME_KEY, entity.getEnumNameCell());
        store.setValue(SPREADSHEET_ENUM_VALUE_KEY, entity.getEnumValueCell());
        Integer startIndex = entity.getStartRepeatRow();
        if (startIndex != null) {
            store.setValue(SPREADSHEET_START_REPEAT_ROW_KEY, String.valueOf(startIndex));
        }
    }

    public void setDefault() {
        store.setDefault(OUTPUT_FOLDER_KEY, "src/main/java");

        store.setDefault(TEMPLATE_FILE_KEY, "");
        store.setDefault(CUSTOM_CODE_SEED_KEY, "");
        store.setDefault(CUSTOM_ROOT_TAG_KEY, "");
        store.setDefault(CUSTOM_ITEMS_TAG_KEY, "");
        store.setDefault(CUSTOM_TAG_HANDLER_KEY, "");

        store.setDefault(SPREADSHEET_PACKAGE_USE_SHEET_KEY, false);
        store.setDefault(SPREADSHEET_PACKAGE_COLUMN_KEY, "B");
        store.setDefault(SPREADSHEET_PACKAGE_ROW_KEY, "1");

        store.setDefault(SPREADSHEET_CLASS_COMMENT_KEY, "B");
        store.setDefault(SPREADSHEET_CLASS_NAME_KEY, "C");
        store.setDefault(SPREADSHEET_ENUM_COMMENT_KEY, "F");
        store.setDefault(SPREADSHEET_ENUM_NAME_KEY, "D");
        store.setDefault(SPREADSHEET_ENUM_VALUE_KEY, "E");
        store.setDefault(SPREADSHEET_START_REPEAT_ROW_KEY, "2");
    }

    public String getDefaultOutputFolder() {
        return store.getDefaultString(OUTPUT_FOLDER_KEY);
    }

    public SpreadsheetPreference getDefaultSpreadsheetCell() {
        SpreadsheetPreference entity = new SpreadsheetPreference();
        entity.setClassCommentCell(store.getDefaultString(SPREADSHEET_CLASS_COMMENT_KEY));
        entity.setClassNameCell(store.getDefaultString(SPREADSHEET_CLASS_NAME_KEY));
        entity.setEnumCommentCell(store.getDefaultString(SPREADSHEET_ENUM_COMMENT_KEY));
        entity.setEnumNameCell(store.getDefaultString(SPREADSHEET_ENUM_NAME_KEY));
        entity.setEnumValueCell(store.getDefaultString(SPREADSHEET_ENUM_VALUE_KEY));
        entity.setPackageUseSheet(store.getDefaultBoolean(SPREADSHEET_PACKAGE_USE_SHEET_KEY));
        entity.setPackageColumnCell(store.getDefaultString(SPREADSHEET_PACKAGE_COLUMN_KEY));
        entity.setPackageRowCell(store.getDefaultString(SPREADSHEET_PACKAGE_ROW_KEY));
        String startIndex = store.getDefaultString(SPREADSHEET_START_REPEAT_ROW_KEY);
        if (!Strings.isNullOrEmpty(startIndex)) {
            entity.setStartRepeatRow(Integer.valueOf(startIndex));
        }
        return entity;

    }
}
