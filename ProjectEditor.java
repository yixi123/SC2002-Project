import java.util.Scanner;

public class ProjectEditor implements IProjectEditor {
    private final ManagerController controller;

    public ProjectEditor(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void editUnits() {
        Scanner sc = new Scanner(System.in);
        Project project = getVisibleProject();

        if (project == null) return;

        System.out.print("New 2-room unit count: ");
        project.setTwoRoomUnits(sc.nextInt());

        System.out.print("New 3-room unit count: ");
        project.setThreeRoomUnits(sc.nextInt());

        System.out.println("✅ Units updated.");
    }

    @Override
    public void editDates() {
        Scanner sc = new Scanner(System.in);
        Project project = getVisibleProject();

        if (project == null) return;

        sc.nextLine(); // consume newline
        System.out.print("New opening date (yyyy-mm-dd): ");
        project.setOpeningDate(java.time.LocalDate.parse(sc.nextLine()));

        System.out.print("New closing date (yyyy-mm-dd): ");
        project.setClosingDate(java.time.LocalDate.parse(sc.nextLine()));

        System.out.println("📅 Dates updated.");
    }

    @Override
    public void editOfficerSlots() {
        Scanner sc = new Scanner(System.in);
        Project project = getVisibleProject();

        if (project == null) return;

        System.out.print("New officer slot count (max 10): ");
        int newSlots = sc.nextInt();

        if (newSlots > 10) {
            System.out.println("❌ Max officer slots = 10");
            return;
        }

        project.setOfficerSlots(newSlots);
        System.out.println("👮 Officer slots updated.");
    }

    private Project getVisibleProject() {
        HDBManager manager = controller.getCurrentManager();

        for (Project p : ProjectDB.getAllProjects()) {
            if (p.getManager().equals(manager) && p.isVisible()) {
                return p;
            }
        }

        System.out.println("❌ No active visible project to edit.");
        return null;
    }
}
