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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.sample.EnumCodeSeed.EnumClassElementsItem;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.spreadsheet.Sheet;

public class GeneratingCodeSeedBaseTest {

    private final ClassElements CLASS_ELEMENTS = new ClassElements() {

        @Override
        public String getPackageName() {
            return null;
        }

        @Override
        public String getClassComment() {
            return null;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public void addClassElementsItem(ClassElementsItem type) {
        }

        @Override
        public List<? extends ClassElementsItem> getClassElementsItem() {
            return null;
        }
    };

    private final GeneratingCodeSeedBase target = new GeneratingCodeSeedBase(false) {

        @Override
        protected String putClassElements(Sheet sheet, String packageName) {
            Class<?> clazz = GeneratingCodeSeedBase.class;
            Field field;
            try {
                field = clazz.getDeclaredField("map");
                field.setAccessible(true);
                @SuppressWarnings("unchecked")
                Map<String, ClassElements> map = (Map<String, ClassElements>) field.get(this);
                map.put("fullClassName", CLASS_ELEMENTS);
                return "fullClassName";
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected ClassElementsItem instanceClassElementsItem(Sheet sheet) {
            return new EnumClassElementsItem(null, null, null);
        }

        @Override
        protected int getStartIndexRepeatingRow() {
            return 0;
        }

        @Override
        protected String getPackageName(Sheet sheet) {
            return "packageName";
        }
    };

    @Mocked
    private Sheet mockSheet;

    @Mocked
    private Iterator<Row> mockIte;

    @Mocked
    private Row mockRow;

    @Test
    public void ifCall_grow() {
        new Expectations() {
            {
                mockSheet.setStartRowIndex(0);
                mockSheet.getCurrentRow();
                result = null;
                mockSheet.recoverBrokenSerial();

                mockSheet.iterator();
                result = mockIte;

                mockIte.hasNext();
                result = true;
                mockIte.next();
                result = mockRow;
                mockSheet.brokenSerial();
                result = true;
                mockSheet.recoverBrokenSerial();

                mockIte.hasNext();
                result = true;
                mockIte.next();
                result = mockRow;
                mockSheet.brokenSerial();
                result = false;

                mockIte.hasNext();
                result = true;
                mockIte.next();
                result = mockRow;
                mockSheet.brokenSerial();
                result = false;

                mockIte.hasNext();
                result = false;
            }
        };

        target.grow(mockSheet);

        Map<String, ClassElements> harvest = target.harvest();
        ClassElements actual = harvest.get("fullClassName");

        assertThat(actual, is(CLASS_ELEMENTS));

    }

}
