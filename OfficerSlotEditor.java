import java.util.Scanner;

public class OfficerSlotEditor implements IOfficerSlotEditor {
    @Override
    public void editOfficerSlots() {
        Scanner sc = new Scanner(System.in);
        Project project = ActiveProjectResolver.getCurrentProject((HDBManager) LoginSession.getUser());

        if (project == null) {
            System.out.println("❌ No active project to edit.");
            return;
        }

        System.out.print("New officer slot count (max 10): ");
        int newSlots = sc.nextInt();

        if (newSlots > 10) {
            System.out.println("❌ Max officer slots = 10");
            return;
        }

        project.setOfficerSlots(newSlots);
        System.out.println("👮 Officer slots updated.");
    }
}