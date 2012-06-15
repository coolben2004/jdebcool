package org.vafer.jdeb.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

/*
 * Admittedly not the nicest way to assert that failOnMissingSrc functions. However, the best that can be done without
 * refactoring, mocking, or extending the scope of the test beyond this unit.
 */
public class DataTestCase extends TestCase {

    private Data data;
    private File missingFile;
    private File file;

    @Override
    protected void setUp() throws Exception {
        data = new Data();
        missingFile = new File("this-file-does-not-exist");
        file = File.createTempFile(getClass().getSimpleName(), "dat");
    }

    @Override
    protected void tearDown() throws Exception {
        if (file != null) {
            file.delete();
        }
    }

    public void testFailOnMissingSrcDefaultFileMissing() throws IOException {
        try {
            data.setSrc(missingFile);
            data.produce(null);
            fail();
        } catch(FileNotFoundException expected) {
        }
    }

    public void testFailOnMissingSrcFalseFileMissing() throws IOException {
        data.setSrc(missingFile);
        data.setFailOnMissingSrc(false);
        data.produce(null);
    }

    public void testFailOnMissingSrcTrueFileMissing() throws IOException {
        try {
            data.setSrc(missingFile);
            data.setFailOnMissingSrc(true);
            data.produce(null);
            fail();
        } catch(FileNotFoundException expected) {
        }
    }

    public void testFailOnMissingSrcDefaultFileExists() throws IOException {
        IOException unknownTypeException = null;
        try {
            data.setSrc(file);
            data.produce(null);
        } catch(IOException expected) {
            unknownTypeException = expected;
        }
        assertTrue(unknownTypeException.getMessage().startsWith("Unknown type"));
    }

    public void testFailOnMissingSrcFalseFileExists() throws IOException {
        IOException unknownTypeException = null;
        try {
            data.setSrc(file);
            data.setFailOnMissingSrc(false);
            data.produce(null);
        } catch(IOException expected) {
            unknownTypeException = expected;
        }
        assertTrue(unknownTypeException.getMessage().startsWith("Unknown type"));
    }

    public void testFailOnMissingSrcTrueFileExists() throws IOException {
        IOException unknownTypeException = null;
        try {
            data.setSrc(file);
            data.setFailOnMissingSrc(true);
            data.produce(null);
        } catch(IOException expected) {
            unknownTypeException = expected;
        }
        assertTrue(unknownTypeException.getMessage().startsWith("Unknown type"));
    }

}
