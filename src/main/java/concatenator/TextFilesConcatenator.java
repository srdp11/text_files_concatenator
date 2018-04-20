package concatenator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TextFilesConcatenator
{
    private File root_;
    private File output_file_;

    public TextFilesConcatenator(String root_path, String output_file_path)
    {
        root_ = new File(root_path);
        output_file_ = new File(output_file_path);
    }

    public boolean process()
    {
        if (!deleteOutputIfExists())
        {
            Logger.error("failed to delete output file " + output_file_.getName());
            return false;
        }

        if (!root_.isDirectory())
        {
            Logger.error(root_.getPath() + " must be directory");
            return false;
        }

        TextFilesSearcher searcher;

        try
        {
            searcher = new TextFilesSearcher(root_);
        }
        catch (IOException ex)
        {
            Logger.error("failed to start search: " + ex.getMessage());
            return false;
        }

        searcher.init();

        File[] text_files = searcher.getTextFiles();

        if (text_files.length == 0)
        {
            Logger.info("text files not found");
            return true;
        }

        Arrays.sort(text_files, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));

        try
        {
            FileUtils.concatFiles(text_files, output_file_);
        }
        catch (IOException ex)
        {
            Logger.error("failed to write text files to " + output_file_.getName() + " cause:");
            Logger.error(ex.getMessage());
            return false;
        }

        return true;
    }

    private boolean deleteOutputIfExists()
    {
        if (output_file_.isFile())
            return output_file_.delete();

        return true;
    }
}
