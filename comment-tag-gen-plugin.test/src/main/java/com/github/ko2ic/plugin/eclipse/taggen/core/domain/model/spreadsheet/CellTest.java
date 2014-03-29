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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CellTest {

    @Test
    public void ifCall_convertToColumnName() {
        String actual = Cell.convertToColumnName(0);
        assertThat(actual, is("A"));
        actual = Cell.convertToColumnName(10);
        assertThat(actual, is("K"));
    }

    @Test
    public void ifCall_convertToColumnIndex() {
        int actual = Cell.convertToColumnIndex("A");
        assertThat(actual, is(0));

        actual = Cell.convertToColumnIndex("K");
        assertThat(actual, is(10));
    }
}
