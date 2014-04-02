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
package com.github.ko2ic.plugin.eclipse.taggen.core.domain.model.preference;

import lombok.Data;

@Data
public class SpreadsheetPreference {

    private boolean packageUseSheet;

    private String basePackage;

    private String packageColumnCell;

    private String packageRowCell;

    private String classCommentCell;

    private String classNameCell;

    private String enumCommentCell;

    private String enumNameCell;

    private String enumValueCell;

    private Integer startRepeatRow;
}
