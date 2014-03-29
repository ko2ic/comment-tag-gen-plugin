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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CommentTagTest {

    private final CommentTag target = new CommentTag() {
    };

    @Test
    public void ifCall_clone() {
        target.setStartLineNumber(2);
        target.setEndLineNumber(10);

        CommentTag actual = target.clone();

        assertThat(actual.getStartLineNumber(), is(2));
        assertThat(actual.getEndLineNumber(), is(10));
    }

}
