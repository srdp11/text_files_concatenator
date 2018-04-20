package concatenator;

public class Main
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("usage <root_dir_path> <output_file>");
            return;
        }

        final String root = args[0];
        final String output_file = args[1];

        TextFilesConcatenator text_files_concatenator = new TextFilesConcatenator(root, output_file);

        if (text_files_concatenator.process())
            Logger.error("success");
        else
            Logger.error("failed");
    }
}
