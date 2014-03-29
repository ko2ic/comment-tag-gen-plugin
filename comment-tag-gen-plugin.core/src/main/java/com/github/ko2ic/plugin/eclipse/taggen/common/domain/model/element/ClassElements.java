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

import java.util.List;

/**
 * Holds informations to generate code.
 * @author ko2ic
 */
public interface ClassElements {

    String getPackageName();

    String getClassComment();

    String getClassName();

    void addClassElementsItem(ClassElementsItem type);

    List<? extends ClassElementsItem> getClassElementsItem();
}
