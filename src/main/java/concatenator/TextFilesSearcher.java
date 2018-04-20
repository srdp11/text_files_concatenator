package concatenator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class TextFilesSearcher
{
    private static ArrayList<String> text_files_extensions_;

    static
    {
        text_files_extensions_ = new ArrayList<>();
        text_files_extensions_.add("txt");
    }

    private ArrayDeque<File> text_files_ = new ArrayDeque<>();
    private ArrayDeque<File> directories_ = new ArrayDeque<>();

    public TextFilesSearcher(File root_dir) throws IOException
    {
        if (root_dir == null)
            throw new IOException("root directory is null");

        if (!root_dir.exists())
            throw new IOException(root_dir.getPath() + " is not exists");

        if (!root_dir.isDirectory())
            throw new IOException(root_dir.getPath() + " is not directory");

        directories_.offer(root_dir);
    }

    public void init()
    {
        while (!directories_.isEmpty())
        {
            File curr_dir = directories_.poll();

            if (curr_dir == null || !curr_dir.exists())
                continue;

            File[] childs = curr_dir.listFiles();

            if (childs == null)
                continue;

            for (File child : childs)
            {
                if (child.isDirectory())
                    directories_.offer(child);
                else if (isTextFile(child))
                {
                    text_files_.offer(child);
                }
            }
        }
    }

    public File[] getTextFiles()
    {
        return text_files_.toArray(new File[text_files_.size()]);
    }

    private boolean isTextFile(File file)
    {
        return file != null &&
               file.isFile() &&
               text_files_extensions_.contains(getFileExtension(file));
    }

    public String getFileExtension(File file)
    {
        final int last_dot_idx = file.getName().lastIndexOf(".");

        if (last_dot_idx <= 0 || file.getName().length() < 2)
            return "";

        return file.getName().substring(last_dot_idx + 1);
    }
}
