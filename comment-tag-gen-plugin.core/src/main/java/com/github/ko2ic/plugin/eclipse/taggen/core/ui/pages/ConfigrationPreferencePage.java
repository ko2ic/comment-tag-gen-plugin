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
package com.github.ko2ic.plugin.eclipse.taggen.core.ui.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.github.ko2ic.plugin.eclipse.taggen.core.Activator;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.CustomCodePreference;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.SpreadsheetPreference;
import com.github.ko2ic.plugin.eclipse.taggen.core.persistense.PreferenceDao;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.ErrorHolderComponent;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.JavaClassPathComponent;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.JavaFilePathComponent;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.SpreadSheetColumnCellComponent;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.SpreadSheetRowCellComponent;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.combo.OutputFolderCombo;
import com.google.common.base.Strings;

public class ConfigrationPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private JavaFilePathComponent templatePathComp;

    private JavaClassPathComponent codeSeedComp;

    private JavaClassPathComponent rootTagComp;

    private JavaClassPathComponent itemsTagComp;

    private JavaClassPathComponent tagHandlerComp;

    private Button packageUseSheetCheck;

    private JavaClassPathComponent basePackageNameComp;

    private SpreadSheetColumnCellComponent packageCellColumnComp;

    private SpreadSheetRowCellComponent packageCellRowComp;

    private SpreadSheetColumnCellComponent classCommentComp;

    private SpreadSheetColumnCellComponent classNameComp;

    private SpreadSheetColumnCellComponent enumCommentComp;

    private SpreadSheetColumnCellComponent enumNameComp;

    private SpreadSheetColumnCellComponent enumValueComp;

    private SpreadSheetRowCellComponent startRepeatRowComp;

    private OutputFolderCombo outputFolderCombo;

    private final PreferenceDao dao = new PreferenceDao();

    private final List<ErrorHolderComponent> packageHasErrors = new ArrayList<>();

    public ConfigrationPreferencePage() {
        // setDescription("description");
    }

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected void performDefaults() {

        outputFolderCombo.select(dao.getDefaultOutputFolder());

        SpreadsheetPreference sheetEntity = dao.getDefaultSpreadsheetCell();
        setText(sheetEntity);

        CustomCodePreference customEntity = dao.getDefaultCustomCode();
        setText(customEntity);

        packageCellColumnComp.setEnabled(true);
        packageCellRowComp.setEnabled(true);
    }

    @Override
    public boolean performOk() {
        String error = getPackageErrorMessages();
        if (!Strings.isNullOrEmpty(error)) {

            IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Package Name shown below is incorrect." + System.getProperty("line.separator") + error);
            Shell shell = Display.getDefault().getActiveShell();
            ErrorDialog.openError(shell, "Validation Error", "Correct the errors shown below.", status);
            return false;
        }

        dao.storeOutputFolder(outputFolderCombo.getText());

        SpreadsheetPreference cellPreference = createSpreadsheetCellPreference();
        dao.storeSpreadsheetCell(cellPreference);
        CustomCodePreference customPreference = createCustomCodePreference();
        dao.storeCustomCode(customPreference);

        return true;
    }

    @Override
    protected Control createContents(Composite parent) {

        CustomCodePreference customEntity = dao.findCustomCodePreference();

        GridLayout layout3 = new GridLayout(3, false);

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setLayout(layout3);

        Label outputFolderLabel = new Label(composite, SWT.NONE);
        outputFolderLabel.setText("Output Folder:");
        GridData colspan2 = new GridData(GridData.VERTICAL_ALIGN_END);
        colspan2.horizontalSpan = 2;
        colspan2.horizontalAlignment = GridData.FILL;
        outputFolderCombo = new OutputFolderCombo(composite, SWT.READ_ONLY);
        outputFolderCombo.setLayoutData(colspan2);
        outputFolderCombo.select(dao.findOutputFolder());

        templatePathComp = new JavaFilePathComponent(composite, "Template File Path:");
        templatePathComp.setText(customEntity.getTemplateFilePath());

        Group customGroup = new Group(parent, SWT.NONE);
        customGroup.setText("Custom implements");
        customGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        customGroup.setLayout(layout3);

        codeSeedComp = new JavaClassPathComponent(customGroup, "CodeSeed:");
        codeSeedComp.setText(customEntity.getCodeSeedImplements());
        packageHasErrors.add(codeSeedComp);

        rootTagComp = new JavaClassPathComponent(customGroup, "RootTag:");
        rootTagComp.setText(customEntity.getRootTagImplements());
        packageHasErrors.add(rootTagComp);

        itemsTagComp = new JavaClassPathComponent(customGroup, "ItemsTag:");
        itemsTagComp.setText(customEntity.getItemsTagImplements());
        packageHasErrors.add(itemsTagComp);

        tagHandlerComp = new JavaClassPathComponent(customGroup, "TagHandler:");
        tagHandlerComp.setText(customEntity.getTagHandlerImplements());
        packageHasErrors.add(tagHandlerComp);

        SpreadsheetPreference spreadEntity = dao.findSpreadsheetPreference();

        GridLayout layout6 = new GridLayout(6, false);

        Group spreadGroup = new Group(parent, SWT.NONE);
        spreadGroup.setText("Default SpreadSheet Cell. (It's a enum generator) ");
        spreadGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        spreadGroup.setLayout(layout6);

        classCommentComp = new SpreadSheetColumnCellComponent(spreadGroup, "Class Comment:");
        classCommentComp.setText(spreadEntity.getClassCommentCell());
        classCommentComp.verify();

        classNameComp = new SpreadSheetColumnCellComponent(spreadGroup, "Class Name:");
        classNameComp.setText(spreadEntity.getClassNameCell());
        classNameComp.verify();

        enumCommentComp = new SpreadSheetColumnCellComponent(spreadGroup, "Enum Comment:");
        enumCommentComp.setText(spreadEntity.getEnumCommentCell());
        enumCommentComp.verify();

        enumNameComp = new SpreadSheetColumnCellComponent(spreadGroup, "Enum Name:");
        enumNameComp.setText(spreadEntity.getEnumNameCell());
        enumNameComp.verify();

        enumValueComp = new SpreadSheetColumnCellComponent(spreadGroup, "Enum Value;");
        enumValueComp.setText(spreadEntity.getEnumValueCell());
        enumValueComp.verify();
        new Label(spreadGroup, SWT.NONE);
        new Label(spreadGroup, SWT.NONE);

        startRepeatRowComp = new SpreadSheetRowCellComponent(spreadGroup, "A row (except empty) to start repeat:");
        startRepeatRowComp.setText(String.valueOf(spreadEntity.getStartRepeatRow()));
        startRepeatRowComp.verify();

        Group packageGroup = new Group(spreadGroup, SWT.NONE);
        packageGroup.setText("Package Name");
        GridData colspan6 = new GridData(GridData.FILL_HORIZONTAL);
        colspan6.horizontalSpan = 6;
        packageGroup.setLayoutData(colspan6);
        packageGroup.setLayout(layout6);

        packageUseSheetCheck = new Button(packageGroup, SWT.CHECK);
        packageUseSheetCheck.setText("use sheet name.");
        packageUseSheetCheck.setSelection(spreadEntity.isPackageUseSheet());
        packageUseSheetCheck.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                Button button = (Button) e.widget;
                setEnabledToUseSheet(button.getSelection());
            }
        });

        packageCellColumnComp = new SpreadSheetColumnCellComponent(packageGroup, "Column:");
        packageCellColumnComp.setText(spreadEntity.getPackageColumnCell());
        packageCellColumnComp.verify();
        packageCellRowComp = new SpreadSheetRowCellComponent(packageGroup, "Row:");
        packageCellRowComp.setText(spreadEntity.getPackageRowCell());
        packageCellRowComp.verify();
        new Label(packageGroup, SWT.NONE);

        basePackageNameComp = new JavaClassPathComponent(packageGroup, "Package Name Base:", false);
        basePackageNameComp.setLayoutData(colspan6);
        basePackageNameComp.setText(spreadEntity.getBasePackage());
        packageHasErrors.add(basePackageNameComp);

        setEnabledToUseSheet(spreadEntity.isPackageUseSheet());

        return parent;
    }

    private void setEnabledToUseSheet(boolean selection) {
        packageCellColumnComp.setEnabled(!selection);
        packageCellRowComp.setEnabled(!selection);
    }

    private void setText(CustomCodePreference customEntity) {
        templatePathComp.setText(customEntity.getTemplateFilePath());
        codeSeedComp.setText(customEntity.getCodeSeedImplements());
        rootTagComp.setText(customEntity.getRootTagImplements());
        itemsTagComp.setText(customEntity.getItemsTagImplements());
        tagHandlerComp.setText(customEntity.getTagHandlerImplements());
    }

    private void setText(SpreadsheetPreference entity) {
        packageCellColumnComp.dispose();
        packageCellRowComp.dispose();
        classCommentComp.dispose();
        classNameComp.dispose();
        enumCommentComp.dispose();
        enumNameComp.dispose();
        enumValueComp.dispose();
        startRepeatRowComp.dispose();

        packageUseSheetCheck.setSelection(entity.isPackageUseSheet());
        basePackageNameComp.setText(entity.getBasePackage());
        packageCellColumnComp.setText(entity.getPackageColumnCell());
        packageCellRowComp.setText(entity.getPackageRowCell());
        classCommentComp.setText(entity.getClassCommentCell());
        classNameComp.setText(entity.getClassNameCell());
        enumCommentComp.setText(entity.getEnumCommentCell());
        enumNameComp.setText(entity.getEnumNameCell());
        enumValueComp.setText(entity.getEnumValueCell());
        Integer startRow = entity.getStartRepeatRow();
        if (startRow != null) {
            startRepeatRowComp.setText(String.valueOf(startRow));
        }

        packageCellColumnComp.verify();
        packageCellRowComp.verify();
        classCommentComp.verify();
        classNameComp.verify();
        enumCommentComp.verify();
        enumNameComp.verify();
        enumValueComp.verify();
        startRepeatRowComp.verify();
    }

    private CustomCodePreference createCustomCodePreference() {
        CustomCodePreference entity = new CustomCodePreference();
        entity.setCodeSeedImplements(codeSeedComp.getText());
        entity.setItemsTagImplements(itemsTagComp.getText());
        entity.setRootTagImplements(rootTagComp.getText());
        entity.setTagHandlerImplements(tagHandlerComp.getText());
        entity.setTemplateFilePath(templatePathComp.getText());
        return entity;
    }

    private SpreadsheetPreference createSpreadsheetCellPreference() {
        SpreadsheetPreference entity = new SpreadsheetPreference();
        entity.setClassCommentCell(classCommentComp.getText());
        entity.setClassNameCell(classNameComp.getText());
        entity.setEnumCommentCell(enumCommentComp.getText());
        entity.setEnumNameCell(enumNameComp.getText());
        entity.setEnumValueCell(enumValueComp.getText());
        entity.setPackageUseSheet(packageUseSheetCheck.getSelection());
        entity.setBasePackage(basePackageNameComp.getText());
        entity.setPackageColumnCell(packageCellColumnComp.getText());
        entity.setPackageRowCell(packageCellRowComp.getText());
        String startRow = startRepeatRowComp.getText();
        if (!Strings.isNullOrEmpty(startRow)) {
            entity.setStartRepeatRow(Integer.valueOf(startRow));
        }
        return entity;
    }

    private String getPackageErrorMessages() {
        StringBuilder sb = new StringBuilder();
        for (ErrorHolderComponent holder : packageHasErrors) {
            if (holder.hasError()) {
                sb.append(holder.getErrorMessage()).append(System.getProperty("line.separator"));
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
