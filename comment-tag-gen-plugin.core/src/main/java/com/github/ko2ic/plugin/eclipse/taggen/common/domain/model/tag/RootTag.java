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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Presents &lt;root&gt; tag.
 * @author ko2ic
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RootTag extends CommentTag {

    private String className;

    private String classComment;

}
