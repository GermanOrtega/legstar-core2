/*******************************************************************************
 * Copyright (c) 2010 LegSem.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     LegSem - initial API and implementation
 ******************************************************************************/
package com.legstar.cob2xsd;

import java.io.File;

import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.legstar.cob2xsd.Cob2XsdMain;

import static org.junit.Assert.*;



/**
 * Test the executable jar.
 * 
 */
public class Cob2XsdMainTest extends AbstractTest {

    /**
     * Test without arguments.
     */
    @Test
    public void testNoArgument() {
        try {
            Cob2XsdMain main = new Cob2XsdMain();
            Options options = main.createOptions();
            assertTrue(main.collectOptions(options, null));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test with help argument.
     */
    @Test
    public void testHelpArgument() {
        try {
            Cob2XsdMain main = new Cob2XsdMain();
            Options options = main.createOptions();
            assertFalse(main.collectOptions(options, new String[] { "-h" }));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test with bad input file.
     */
    @Test
    public void testWrongInputArgument() {
        try {
            Cob2XsdMain main = new Cob2XsdMain();
            Options options = main.createOptions();
            main.collectOptions(options, new String[] { "-i nope" });
            fail();
        } catch (Exception e) {
            assertEquals(
                    "java.lang.IllegalArgumentException: Input file or folder nope not found",
                    e.toString());
        }
    }

    /**
     * Test with unsupported argument.
     */
    @Test
    public void testUnsupportedArgument() {
        try {
            Cob2XsdMain main = new Cob2XsdMain();
            Options options = main.createOptions();
            main.collectOptions(options, new String[] { "- #" });
        } catch (Exception e) {
            assertEquals(
                    "org.apache.commons.cli.UnrecognizedOptionException: Unrecognized option: - #",
                    e.toString());
        }
    }

    /**
     * Test with configuration argument.
     */
    @Test
    public void testConfigurationArgument() {
        Cob2XsdMain main = new Cob2XsdMain();
        try {
            main.execute(new String[] { "-c",
                    "src/main/resources/cob2xsd.properties", "-i",
                    COBOL_SAMPLES_DIR + "/LSFILEAE", "-o",
                    GEN_XSD_DIR + "/myfile.xsd" });
            File result = new File(GEN_XSD_DIR + "/myfile.xsd");
            assertTrue(result.exists());
            assertTrue(FileUtils.readFileToString(result).contains(
                    "xmlns:tns=\"http://legstar.com/lsfileae\""));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}