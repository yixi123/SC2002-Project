public class VisibilityToggler implements IVisibilityToggler {
    @Override
    public void toggleVisibility(Project selectedProject) {
        HDBManager manager = (HDBManager) LoginSession.getUser();

        if (!selectedProject.getManager().equals(manager)) {
            System.out.println("❌ Not your project.");
            return;
        }

        if (!selectedProject.isVisible()) {
            if (ActiveProjectResolver.getCurrentProject(manager) != null) {
                System.out.println("❌ You already have an active project.");
                return;
            }

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
}