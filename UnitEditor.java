import java.util.Scanner;

public class UnitEditor implements IUnitEditor {
    @Override
    public void editUnits() {
        Scanner sc = new Scanner(System.in);
        Project project = ActiveProjectResolver.getCurrentProject((HDBManager) LoginSession.getUser());

        if (project == null) {
            System.out.println("❌ No active project to edit.");
            return;
        }

        System.out.print("New 2-room unit count: ");
        project.setTwoRoomUnits(sc.nextInt());

        System.out.print("New 3-room unit count: ");
        project.setThreeRoomUnits(sc.nextInt());

        System.out.println("✅ Units updated.");
    }
}