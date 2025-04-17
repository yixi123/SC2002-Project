public class VisibilityToggler implements IVisibilityToggler {
    private final ManagerController controller;

    public VisibilityToggler(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void toggleVisibility(Project selectedProject) {
        HDBManager manager = controller.getCurrentManager();

        if (!selectedProject.getManager().equals(manager)) {
            System.out.println("❌ Not your project.");
            return;s
        }

        if (!selectedProject.isVisible()) {
            if (hasActiveProject(manager)) {
                System.out.println("❌ You already have an active project.");
                return;
            }

            // Set all manager's projects to invisible
            for (Project p : ProjectDB.getAllProjects()) {
                if (p.getManager().equals(manager)) {
                    p.setVisible(false);
                }
            }

            selectedProject.setVisible(true);
            System.out.println("✅ Project is now visible.");
        } else {
            selectedProject.setVisible(false);
            System.out.println("🔕 Project visibility turned off.");
        }
    }

    private boolean hasActiveProject(HDBManager manager) {
        for (Project p : ProjectDB.getAllProjects()) {
            if (p.getManager().equals(manager) && p.isVisible()) {
                return true;
            }
        }
        return false;
    }
}
