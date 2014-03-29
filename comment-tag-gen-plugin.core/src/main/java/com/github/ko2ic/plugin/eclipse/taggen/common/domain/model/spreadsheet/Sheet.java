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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet;

import org.apache.poi.ss.usermodel.Row;

public interface Sheet extends Iterable<Row> {

    String getSheetName();

    void setStartRowIndex(int startIndexRepeatingRow);

    Row getCurrentRow();

    void recoverBrokenSerial();

    boolean brokenSerial();

    String getStringCellValue(int cellIndexClassName);

    String getStringCellValue(int i, int j);

}
