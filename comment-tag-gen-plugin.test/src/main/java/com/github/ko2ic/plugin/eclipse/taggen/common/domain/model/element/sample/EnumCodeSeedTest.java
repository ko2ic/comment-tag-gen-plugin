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
package com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Test;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample.EnumCodeSeed.EnumClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.valueobject.NameRuleString;

public class EnumCodeSeedTest {

    private final EnumCodeSeed target = new EnumCodeSeed(true);

    @Mocked
    private Sheet mockSheet;

    @Test
    public void ifCall_getStartIndexRepeatingRow() {
        int actual = target.getStartIndexRepeatingRow();
        assertThat(actual, is(3));
    }

    @Test
    public void ifCall_getPackageName() {
        new NonStrictExpectations() {
            {
                mockSheet.getStringCellValue(0, 1);
                result = "packageName";
            }
        };
        String actual = target.getPackageName(mockSheet);
        assertThat(actual, is("packageName"));

        new Verifications() {
            {
                mockSheet.getStringCellValue(0, 1);
            }
        };
    }

    @Test
    public void ifCall_instanceClassElementsItem() {
        new Expectations() {
            {
                mockSheet.getStringCellValue(3);
                result = "name";
                mockSheet.getStringCellValue(4);
                result = "code";
                mockSheet.getStringCellValue(5);
                result = "comment";
            }
        };
        EnumCodeSeed target = new EnumCodeSeed(true);
        EnumClassElementsItem actual = (EnumClassElementsItem) target.instanceClassElementsItem(mockSheet);
        EnumClassElementsItem expected = new EnumClassElementsItem(new NameRuleString("name"), "code", "comment");
        assertThat(actual.getCode(), is(expected.getCode()));
        assertThat(actual.getComment(), is(expected.getComment()));
        assertThat(actual.getName().toLowerCase(), is(expected.getName().toLowerCase()));
    }

    @Test
    public void ifCall_putClassElements() {

        new NonStrictExpectations() {
            {
                mockSheet.getStringCellValue(2);
                result = "definedClassName";
            }
        };

        String fullClassName = target.putClassElements(mockSheet, "packageName");
        assertThat(fullClassName, is("packageName.DefinedClassName"));
    }

    public static class InCaseOf_EnumClassElementsItemClass {

        @Mocked
        private NameRuleString mock;

        EnumClassElementsItem target = new EnumClassElementsItem(mock, "", "");

        @Test
        public void ifCall_getUpperCamelName() {
            new Expectations() {
                {
                    mock.toUpperCamel();
                    result = "";
                }
            };
            target.getUpperCamelName();
            new Verifications() {
                {
                    mock.toUpperCamel();
                }
            };
        }

        @Test
        public void ifCall_getUpperSnakeName() {
            new Expectations() {
                {
                    mock.phraseConstantName();
                    result = "";
                }
            };
            target.getUpperSnakeName();
            new Verifications() {
                {
                    mock.phraseConstantName();
                }
            };
        }

        @Test
        public void ifCall_getUpperName() {
            new Expectations() {
                {
                    mock.toUpperCase();
                    result = "";
                }
            };
            target.getUpperName();
            new Verifications() {
                {
                    mock.toUpperCase();
                }
            };
        }
    }

}
