package test.concatenator;

import concatenator.FileUtils;
import concatenator.TextFilesConcatenator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TextFilesConcatenatorTest
{
    private static final String TEST_ROOT_FOLDER_NAME = "test_root";
    private static final String EMPTY_TEST_ROOT_FOLDER_NAME = "empty_root";

    private File empty_test_folder_;
    private File test_folder_;
    private File report_;

    @Rule
    public TemporaryFolder folder_ = new TemporaryFolder();

    @Before public void setUp() throws IOException
    {
        initEmptyTestFolder();
        initTestFolder();
        report_ = folder_.newFile("report.txt");
    }

    @Test
    public void testRootWithTextFiles() throws IOException
    {
        TextFilesConcatenator concatenator = new TextFilesConcatenator(test_folder_.getPath(), report_.getPath());

        assertTrue(concatenator.process());

        assertEquals("some_data\nanother_data\nwow\n", FileUtils.readFile(report_));
    }

    @Test
    public void testEmptyRoot()
    {
        TextFilesConcatenator concatenator = new TextFilesConcatenator(empty_test_folder_.getPath(), report_.getPath());

        assertTrue(concatenator.process());
        assertFalse(report_.exists());
    }

    private void initTestFolder() throws IOException
    {
        test_folder_ = folder_.newFolder(TEST_ROOT_FOLDER_NAME);

        FileUtils.createFolder(test_folder_, "empty_folder");

        File folder_with_text_file = FileUtils.createFolder(test_folder_, "folder_with_files");
        FileUtils.createFile(folder_with_text_file, "b.txt", "another_data");
        FileUtils.createFile(folder_with_text_file, "not_text_file", "SOME RAW DATA");

        FileUtils.createFolder(folder_with_text_file, "empty_folder");
        FileUtils.createFile(folder_with_text_file, "a.txt", "some_data");
        FileUtils.createFile(folder_with_text_file, "c.txt", "wow");
    }

    private void initEmptyTestFolder() throws IOException
    {
        empty_test_folder_ = folder_.newFolder(EMPTY_TEST_ROOT_FOLDER_NAME);
    }
}
