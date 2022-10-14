package util;

import java.io.*;

public class Files {
    /**
     * Read specified file from package.
     * @param file Name of file to read.
     * @return contents of file as string.
     */
    private static String ReadText(String file)  {
        try {// Get a string stream of the specified file.
            BufferedReader br = new BufferedReader(new FileReader("Data/" + file));


            // Get a string builder.
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null)
                // Iterate over lines in the string stream and append to builder;
                sb.append(line).append('\n');

            // close reader
            br.close();

            // pipe out final string.
            return sb.toString();
        } catch (Exception e) {
            System.out.println("Error reading file " + file);
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * Read ProductInfo.txt.
     * @return contents of ProductInfo.txt
     */
    public static String GetProductInfo() {
        return ReadText("ProductInfo.txt");
    }

    /**
     * Read BuyerInfo.txt.
     * @return contents of BuyerInfo.txt
     */
    public static String GetBuyerInfo() {
        return ReadText("BuyerInfo.txt");
    }

    /**
     * Read SellerInfo.txt.
     * @return contents of SellerInfo.txt
     */
    public static String GetSellerInfo() {
        return ReadText("SellerInfo.txt");
    }

    /**
     * Read UserProduct.txt.
     * @return contents of UserProduct.txt
     */
    public static String GetUserProductInfo() {
        return ReadText("UserProduct.txt");
    }

    public static void WriteLineToFile(String fileName, String newLine) throws IOException {
        var br = new BufferedWriter(new FileWriter("Data/" + fileName, true));
        br.append(newLine).append("\n");
        br.close();
    }
}
