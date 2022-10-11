package Input;

import java.io.InputStream;
import java.util.Scanner;

public class Input {
    /**
     * Read specified file from package.
     * @param file Name of file to read.
     * @return contents of file as string.
     */
    private static String ReadText(String file) {
        // Get a string stream of the specified file.
        InputStream stringStream = Input.class.getResourceAsStream(file);

        // Get a string builder.
        StringBuilder sb = new StringBuilder();
        for (Scanner sc = new Scanner(stringStream); sc.hasNext(); )
            // Iterate over lines in the string stream and append to builder;
            sb.append(sc.nextLine()).append('\n');

        // pipe out final string.
        return sb.toString();
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
}
