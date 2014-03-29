package com.github.ko2ic.plugin.eclipse.taggen.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Sample integration test. In Eclipse, right-click > Run As > JUnit-Plugin. <br/>
 * In Maven CLI, run "mvn integration-test".
 */
public class ActivatorTest {

    @Test
    public void veryStupidTest() {
        assertEquals("comment-tag-gen-plugin.core", Activator.PLUGIN_ID);
        assertTrue("Plugin should be started", Activator.getDefault().started);
    }
}