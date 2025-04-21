package view;

/**
 * The ViewFormatter class provides utility methods for formatting output in the console.
 * It includes methods to generate break lines for better visual separation.
 */
public class ViewFormatter {

    /**
     * Generates a standard break line.
     *
     * @return A string representing a standard break line.
     */
    public static String breakLine() {
        return "-------------------------------------------";
    }

    /**
     * Generates a thick break line.
     *
     * @return A string representing a thick break line.
     */
    public static String thickBreakLine() {
        return "===========================================";
    }
}
