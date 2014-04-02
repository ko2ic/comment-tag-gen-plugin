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

import java.lang.reflect.Constructor;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.GeneratingCodeSeedBase;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample.EnumCodeSeed;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.sample.EnumItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.service.parse.TagHandlerBase;
import com.github.ko2ic.plugin.eclipse.taggen.common.service.parse.sample.EnumTagHandler;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.CustomCodePreference;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference.SpreadsheetPreference;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.TemplateCode;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.AppFileNotFoundException;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.AppNotImplementsException;
import com.github.ko2ic.plugin.eclipse.taggen.core.persistense.PreferenceDao;
import com.google.common.base.Strings;

public class CustomCodeFinder {

    private final WorkspaceClassLoader loader = new WorkspaceClassLoader();

    private final CustomCodePreference codePreference;

    private final SpreadsheetPreference spreadPreference;

    public CustomCodeFinder() {
        PreferenceDao dao = new PreferenceDao();
        this.codePreference = dao.findCustomCodePreference();
        this.spreadPreference = dao.findSpreadsheetPreference();
    }

    public String findTemplateFilePath() throws AppFileNotFoundException {
        String path = codePreference.getTemplateFilePath();
        if (Strings.isNullOrEmpty(path)) {
            return TemplateCode.getDefaultTemplateCodePath();
        }
        return path;
    }

    public GeneratingCodeSeedBase findCodeSeed() throws AppNotImplementsException {
        String basePackage = spreadPreference.getBasePackage();
        boolean whetherPackageNameUsesSheet = spreadPreference.isPackageUseSheet();
        String fullyQualifiedName = codePreference.getCodeSeedImplements();
        if (Strings.isNullOrEmpty(fullyQualifiedName)) {
            return new EnumCodeSeed(basePackage, whetherPackageNameUsesSheet);
        }
        try {
            Class<?> clazz = loader.loadClass(fullyQualifiedName);
            Constructor<?> cons = clazz.getDeclaredConstructor(new Class[] {Boolean.class });
            return (GeneratingCodeSeedBase) cons.newInstance(new Object[] {whetherPackageNameUsesSheet });

        } catch (Exception e) {
            throw new AppNotImplementsException(fullyQualifiedName, GeneratingCodeSeedBase.class.getName());
        }
    }

    public RootTag findRootTag() throws AppNotImplementsException {
        String fullyQualifiedName = codePreference.getRootTagImplements();
        if (Strings.isNullOrEmpty(fullyQualifiedName)) {
            return new RootTag();
        }
        try {
            Class<?> clazz = loader.loadClass(fullyQualifiedName);
            return (RootTag) clazz.newInstance();
        } catch (Exception e) {
            throw new AppNotImplementsException(fullyQualifiedName, RootTag.class.getName());
        }
    }

    public ItemsTag findItemsTag() throws AppNotImplementsException {
        String fullyQualifiedName = codePreference.getItemsTagImplements();
        if (Strings.isNullOrEmpty(fullyQualifiedName)) {
            return new EnumItemsTag();
        }
        try {
            Class<?> clazz = loader.loadClass(fullyQualifiedName);
            return (ItemsTag) clazz.newInstance();
        } catch (Exception e) {
            throw new AppNotImplementsException(fullyQualifiedName, ItemsTag.class.getName());
        }
    }

    public TagHandlerBase findTagHandler(RootTag rootTag) throws AppNotImplementsException {
        String fullyQualifiedName = codePreference.getTagHandlerImplements();
        if (Strings.isNullOrEmpty(fullyQualifiedName)) {
            return new EnumTagHandler(rootTag);
        }
        try {
            Class<?> clazz = loader.loadClass(fullyQualifiedName);
            Constructor<?> cons = clazz.getDeclaredConstructor(new Class[] {RootTag.class });
            return (TagHandlerBase) cons.newInstance(new Object[] {rootTag });
        } catch (Exception e) {
            throw new AppNotImplementsException(fullyQualifiedName, TagHandlerBase.class.getName());
        }

    }
}
