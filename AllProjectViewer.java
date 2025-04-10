public class AllProjectViewer implements IAllProjectViewer {
    @Override
    public void viewAllProjects() {
        for (Project p : ProjectDB.getAllProjects()) {
            System.out.println(p);
        }
    }
}