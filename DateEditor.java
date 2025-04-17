import java.time.LocalDate;
import java.util.Scanner;

public class DateEditor implements IDateEditor {
    @Override
    public void editDates() {
        Scanner sc = new Scanner(System.in);
        HDBManager manager = (HDBManager) LoginSession.getUser();

        // Loop through all projects and find the active one
        Project project = null;
        for (Project p : ProjectDB.getAllProjects()) {
            if (p.getManager().equals(manager) && p.isVisible()) {
                project = p;
                break;
            }
        }

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
