public class ProjectDeleter implements IProjectDeleter {
    @Override
    public void deleteProject(String projectName) {
        Project project = ProjectDB.findByName(projectName);
        HDBManager manager = (HDBManager) LoginSession.getUser();

        if (project == null || !project.getManager().equals(manager)) {
            System.out.println("❌ You can only delete your own projects.");
            return;
        }

        ProjectDB.removeProject(project);
        System.out.println("🗑️ Project deleted.");
    }
}
