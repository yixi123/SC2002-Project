import java.time.LocalDate;
import java.util.Scanner;

public class DateEditor implements IDateEditor {
    @Override
    public void editDates() {
        Scanner sc = new Scanner(System.in);
        Project project = ActiveProjectResolver.getCurrentProject((HDBManager) LoginSession.getUser());

        if (project == null) {
            System.out.println("❌ No active project to edit.");
            return;
        }

        System.out.print("New opening date (yyyy-mm-dd): ");
        project.setOpeningDate(LocalDate.parse(sc.nextLine()));

        System.out.print("New closing date (yyyy-mm-dd): ");
        project.setClosingDate(LocalDate.parse(sc.nextLine()));

        System.out.println("📅 Dates updated.");
    }
}