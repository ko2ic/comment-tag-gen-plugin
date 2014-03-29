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
package com.github.ko2ic.plugin.eclipse.taggen.core.service.merge;

import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;

/**
 * Merges Java Code.
 * @author ko2ic
 */
public class EclipseJavaMerger {

    private final JMerger merger = new JMerger(buildMergeRule());

    public String merge(String source, String target) {
        try {
            if (target == null) {
                return source;
            }
            merger.setSourceCompilationUnit(merger.createCompilationUnitForContents(source));
            merger.setTargetCompilationUnit(merger.createCompilationUnitForContents(target));
            merger.merge();
            return merger.getTargetCompilationUnitContents();
        } finally {
            merger.reset();
        }
    }

    private JControlModel buildMergeRule() {
        JControlModel controlModel = new JControlModel();
        String configFileUri = getClass().getResource("/jmerge.xml").toString();
        controlModel.initialize(new ASTFacadeHelper(), configFileUri);
        return controlModel;
    }
}
