package view;

/**
 * The ViewFormatter class provides utility methods for formatting output in the console.
 * It includes methods to generate break lines for better visual separation.
 */
public class ViewFormatter {

    /**
     * Generates and returns a standard thin horizontal divider used to visually separate sections in the UI.
     *
     * @return a string representing a thin break line
     */
    public static String breakLine(){
        return "-------------------------------------------";
      }
  
      /**
       * Generates and returns a thick horizontal divider for highlighting important sections or headings.
       *
       * @return a string representing a thick break line
       */
      public static String thickBreakLine(){
        return "===========================================";
      }
  }
  
