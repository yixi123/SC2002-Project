import java.time.LocalDate;
import java.util.Scanner;

public class ProjectCreator implements IProjectCreator {
    @Override
    public void createProject() {
        Scanner sc = new Scanner(System.in);
        HDBManager manager = (HDBManager) LoginSession.getUser();

        if (ActiveProjectResolver.getCurrentProject(manager) != null) {
            System.out.println("❌ You already manage an active project.");
            return;
        }

        System.out.print("Project name: ");
        String name = sc.nextLine();

        if (ProjectDB.findByName(name) != null) {
            System.out.println("❌ Project name must be unique.");
            return;
        }

        System.out.print("Opening date (yyyy-mm-dd): ");
        LocalDate opening = LocalDate.parse(sc.nextLine());
        System.out.print("Closing date (yyyy-mm-dd): ");
        LocalDate closing = LocalDate.parse(sc.nextLine());

        System.out.print("2-room units: ");
        int twoRoom = sc.nextInt();
        System.out.print("3-room units: ");
        int threeRoom = sc.nextInt();
        sc.nextLine();

        System.out.print("Max officer slots: ");
        int officerSlots = sc.nextInt();

        Project project = new Project(name, opening, closing, twoRoom, threeRoom, officerSlots, manager);
        ProjectDB.addProject(project);

        System.out.println("✅ Project created.");
    }
}