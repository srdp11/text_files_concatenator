package concatenator;

import java.io.*;

public class FileUtils
{
    static void concatFiles(File[] src, File dest) throws IOException
    {
        if (dest == null)
            throw new IOException("destination file is null");

        try (Writer out = getFileWriter(dest))
        {
            for (File file : src)
            {
                if (file != null && file.isFile())
                    transferFileToWriter(out, file);
            }
        }
    }

    public static File createFile(File parent, String file_name, String data) throws IOException
    {
        if (parent == null)
            throw new IOException("paren directory is null");

        if (!parent.isDirectory())
            throw new IOException(parent.getPath() + " must be directory");

        File file = new File(parent.getPath() + "/" + file_name);

        if (file.exists())
            throw new IOException("file " + file.getPath() + " is already exists");

        if (!file.createNewFile())
            throw new IOException("failed to create file " + file.getPath());

        writeToFile(file ,data);
        return file;
    }

    public static File createFolder(File parent, String folder_name) throws IOException
    {
        if (parent == null)
            throw new IOException("paren directory is null");

        if (!parent.isDirectory())
            throw new IOException(parent.getPath() + " must be directory");

        File dir = new File(parent.getPath() + "/" + folder_name);

        if (dir.exists())
            throw new IOException("dir " + dir.getPath() + " is already exists");

        if (!dir.mkdir())
            throw new IOException("failed to create dir " + dir.getPath());

        return dir;
    }

    public static void writeToFile(File file, String data) throws IOException
    {
        try (Writer out = getFileWriter(file))
        {
            out.write(data);
        }
    }

    public static String readFile(File file) throws IOException
    {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader in = getFileReader(file))
        {
            String buffer;

            while ((buffer = in.readLine()) != null)
            {
                sb.append(buffer);
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    private static void transferFileToWriter(Writer dest, File src) throws IOException
    {
        try (BufferedReader in = getFileReader(src))
        {
            String buffer;

            while ((buffer = in.readLine()) != null)
            {
                dest.write(buffer);
                dest.write(System.lineSeparator());
            }
        }
    }

    private static BufferedReader getFileReader(File file) throws FileNotFoundException
    {
        return new BufferedReader(new FileReader(file));
    }

    private static BufferedWriter getFileWriter(File file) throws FileNotFoundException
    {
        // TODO: why so long ? (see ^)
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }
}
