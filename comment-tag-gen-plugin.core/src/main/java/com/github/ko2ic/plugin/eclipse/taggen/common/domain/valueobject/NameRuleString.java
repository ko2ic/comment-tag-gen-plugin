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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject;

import com.google.common.base.CaseFormat;

public class NameRuleString {

    private final String str;

    public NameRuleString(String str) {
        this.str = str;
    }

    public String phraseClassName() {
        return toUpperCamel();
    }

    public String phraseVariableName() {
        String value = str;
        if (Character.isUpperCase(str.charAt(0))) {
            if (str.contains("_")) {
                value = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
            } else {
                value = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, str);
            }
        } else {
            if (str.contains("_")) {
                value = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
            }
        }
        return value;
    }

    public String phraseConstantName() {
        String value = str;
        if (Character.isUpperCase(str.charAt(0))) {
            if (!str.contains("_")) {
                value = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, str);
            }
        } else {
            if (str.contains("_")) {
                value = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, str);
            } else {
                value = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, str);
            }
        }
        return value;
    }

    public String toUpperCamel() {
        String value = str;
        if (Character.isUpperCase(str.charAt(0))) {
            if (str.contains("_")) {
                value = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
            }
        } else {
            if (str.contains("_")) {
                value = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
            } else {
                value = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, str);
            }
        }
        return value;
    }

    public String toUpperCase() {
        if (str != null) {
            return str.toUpperCase();
        }
        return null;
    }

    public String toLowerCase() {
        if (str != null) {
            return str.toLowerCase();
        }
        return null;
    }
}
