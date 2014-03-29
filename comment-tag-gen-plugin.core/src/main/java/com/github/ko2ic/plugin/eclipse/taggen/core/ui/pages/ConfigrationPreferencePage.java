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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.CustomCodePreference;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.SpreadsheetPreference;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.SystemException;
import com.github.ko2ic.plugin.eclipse.taggen.core.persistense.PreferenceDao;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.AlphameticVerifyListener;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.NumericVerifyListener;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.commponents.JavaSelectDialog;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.commponents.OutputFolderCombo;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

public class ConfigrationPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private Text templateText;

    private Text codeSeedText;

    private Text rootTagText;

    private Text itemsTagText;

    private Text tagHandlerText;

    private Button packageUseSheetCheck;

    private Text packageCellColumnText;

    private Text packageCellRowText;

    private Text classCommentText;

    private Text classNameText;

    private Text enumCommentText;

    private Text enumNameText;

    private Text enumValueText;

    private Text startRepeatRowText;

    private OutputFolderCombo outputFolderCombo;

    private final PreferenceDao dao = new PreferenceDao();

    public ConfigrationPreferencePage() {
        setDescription("description");

    }

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected void performDefaults() {
        outputFolderCombo.select(dao.getDefaultOutputFolder());

        SpreadsheetPreference cellEntity = dao.getDefaultSpreadsheetCell();
        setText(cellEntity);
    }

    @Override
    public boolean performOk() {
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

        Label templateLabel = new Label(composite, SWT.NONE);
        templateLabel.setText("Template File Path:");
        templateText = new Text(composite, SWT.SINGLE | SWT.BORDER);
        templateText.setText(customEntity.getTemplateFilePath());
        templateText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        createJavaSelectButton(composite, new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                JavaSelectDialog dialog = new JavaSelectDialog(new Shell(), "Please select a custom template.");
                dialog.open();
                ICompilationUnit unit = dialog.getCompilationUnit();
                templateText.setText(unit.getPath().toFile().getPath());
                dialog.close();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        Group customGroup = new Group(parent, SWT.NONE);
        customGroup.setText("Custom implements");
        customGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        customGroup.setLayout(layout3);

        codeSeedText = createCustomJavaSelectComponent(customGroup, "CodeSeed:", customEntity.getCodeSeedImplements());
        rootTagText = createCustomJavaSelectComponent(customGroup, "RootTag:", customEntity.getRootTagImplements());
        itemsTagText = createCustomJavaSelectComponent(customGroup, "ItemsTag:", customEntity.getItemsTagImplements());
        tagHandlerText = createCustomJavaSelectComponent(customGroup, "TagHandler:", customEntity.getTagHandlerImplements());

        SpreadsheetPreference spreadEntity = dao.findSpreadsheetCellPreference();

        GridLayout layout6 = new GridLayout(6, false);

        Group spreadGroup = new Group(parent, SWT.NONE);
        spreadGroup.setText("Default SpreadSheet Cell. (It's a enum generator) ");
        spreadGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        spreadGroup.setLayout(layout6);

        classCommentText = createTextFieldComponent(spreadGroup, "Class Comment:", spreadEntity.getClassCommentCell());
        classCommentText.addVerifyListener(new AlphameticVerifyListener());
        classNameText = createTextFieldComponent(spreadGroup, "Class Name:", spreadEntity.getClassNameCell());
        classNameText.addVerifyListener(new AlphameticVerifyListener());

        enumCommentText = createTextFieldComponent(spreadGroup, "Enum Comment:", spreadEntity.getEnumCommentCell());
        enumCommentText.addVerifyListener(new AlphameticVerifyListener());
        enumNameText = createTextFieldComponent(spreadGroup, "Enum Name:", spreadEntity.getEnumNameCell());
        enumNameText.addVerifyListener(new AlphameticVerifyListener());
        enumValueText = createTextFieldComponent(spreadGroup, "Enum Value;", spreadEntity.getEnumValueCell());
        enumValueText.addVerifyListener(new AlphameticVerifyListener());

        startRepeatRowText = createTextFieldStartRowComponent(spreadGroup, "A row (except empty) to start repeat:", String.valueOf(spreadEntity.getStartRepeatRow()));

        Group packageGroup = new Group(spreadGroup, SWT.NONE);
        packageGroup.setText("Package Name");
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 6;
        packageGroup.setLayoutData(gridData);
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

        packageCellColumnText = createTextFieldComponent(packageGroup, "Column:", spreadEntity.getPackageColumnCell());
        packageCellRowText = createTextFieldComponent(packageGroup, "Row:", spreadEntity.getPackageRowCell());
        packageCellRowText.addVerifyListener(new NumericVerifyListener());

        setEnabledToUseSheet(spreadEntity.isPackageUseSheet());

        return parent;
    }

    private void setEnabledToUseSheet(boolean selection) {
        packageCellColumnText.setEnabled(!selection);
        packageCellRowText.setEnabled(!selection);
    }

    protected Text createCustomJavaSelectComponent(Group group, String labelText, String textValue) {
        Text text = createTextFieldComponent(group, labelText, textValue);
        createCustomJavaSelectButton(group, text);
        return text;
    }

    protected Text createTextFieldStartRowComponent(Group group, String labelText, String textValue) {
        Label label = new Label(group, SWT.NONE);
        label.setText(labelText);
        GridData colspan3 = new GridData(GridData.VERTICAL_ALIGN_END);
        colspan3.horizontalSpan = 3;
        colspan3.horizontalAlignment = GridData.FILL;
        label.setLayoutData(colspan3);
        Text text = new Text(group, SWT.SINGLE | SWT.BORDER);
        text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        text.setText(textValue);
        text.addVerifyListener(new NumericVerifyListener());
        GridData colspan2 = new GridData(GridData.VERTICAL_ALIGN_END);
        colspan2.horizontalSpan = 2;
        colspan2.horizontalAlignment = GridData.FILL;
        new Label(group, SWT.NONE).setLayoutData(colspan2);
        return text;
    }

    protected Text createTextFieldComponent(Group group, String labelText, String textValue) {
        Label label = new Label(group, SWT.NONE);
        label.setText(labelText);
        Text text = new Text(group, SWT.SINGLE | SWT.BORDER);
        text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        text.setText(textValue);
        return text;
    }

    protected void createJavaSelectButton(Composite comp, SelectionListener listener) {
        Button button = new Button(comp, SWT.PUSH);
        button.setText(JFaceResources.getString("openBrowse"));
        button.addSelectionListener(listener);
    }

    protected void createCustomJavaSelectButton(Composite comp, final Text text) {

        createJavaSelectButton(comp, new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                JavaSelectDialog dialog = new JavaSelectDialog(new Shell(), "Please select a custom class.");
                dialog.open();
                ICompilationUnit unit = dialog.getCompilationUnit();
                String javaFileName = unit.getElementName();
                String javaName = javaFileName.replaceFirst(".java$", "");
                try {
                    IPackageDeclaration declaration = unit.getPackageDeclarations()[0];
                    String packageName = declaration.getElementName();
                    String fullName = packageName + "." + javaName;
                    text.setText(fullName);
                } catch (JavaModelException t) {
                    throw Throwables.propagate(new SystemException(t));
                } finally {
                    dialog.close();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
    }

    // private void setText(CustomCodePreference customEntity) {
    // templateText.setText(customEntity.getTemplateFilePath());
    // codeSeedText.setText(customEntity.getCodeSeedImplements());
    // rootTagText.setText(customEntity.getRootTagImplements());
    // itemsTagText.setText(customEntity.getItemsTagImplements());
    // tagHandlerText.setText(customEntity.getTagHandlerImplements());
    // }

    private void setText(SpreadsheetPreference cellEntity) {
        packageUseSheetCheck.setSelection(cellEntity.isPackageUseSheet());
        packageCellColumnText.setText(cellEntity.getPackageColumnCell());
        packageCellRowText.setText(cellEntity.getPackageRowCell());
        classCommentText.setText(cellEntity.getClassCommentCell());
        classNameText.setText(cellEntity.getClassNameCell());
        enumCommentText.setText(cellEntity.getEnumCommentCell());
        enumNameText.setText(cellEntity.getEnumNameCell());
        enumValueText.setText(cellEntity.getEnumValueCell());
        Integer startRow = cellEntity.getStartRepeatRow();
        if (startRow != null) {
            startRepeatRowText.setText(String.valueOf(startRow));
        }
    }

    private CustomCodePreference createCustomCodePreference() {
        CustomCodePreference entity = new CustomCodePreference();
        entity.setCodeSeedImplements(codeSeedText.getText());
        entity.setItemsTagImplements(itemsTagText.getText());
        entity.setRootTagImplements(rootTagText.getText());
        entity.setTagHandlerImplements(tagHandlerText.getText());
        entity.setTemplateFilePath(templateText.getText());
        return entity;
    }

    private SpreadsheetPreference createSpreadsheetCellPreference() {
        SpreadsheetPreference entity = new SpreadsheetPreference();
        entity.setClassCommentCell(classCommentText.getText());
        entity.setClassNameCell(classNameText.getText());
        entity.setEnumCommentCell(enumCommentText.getText());
        entity.setEnumNameCell(enumNameText.getText());
        entity.setEnumValueCell(enumValueText.getText());
        entity.setPackageUseSheet(packageUseSheetCheck.getSelection());
        entity.setPackageColumnCell(packageCellColumnText.getText());
        entity.setPackageRowCell(packageCellRowText.getText());
        String startRow = startRepeatRowText.getText();
        if (!Strings.isNullOrEmpty(startRow)) {
            entity.setStartRepeatRow(Integer.valueOf(startRow));
        }
        return entity;
    }
}
