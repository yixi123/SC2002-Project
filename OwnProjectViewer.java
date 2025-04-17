public class OwnProjectViewer implements IOwnProjectViewer {
    private final ManagerController controller;

    public OwnProjectViewer(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void viewOwnProjects() {
        HDBManager manager = controller.getCurrentManager();
        for (Project p : ProjectDB.getAllProjects()) {
            if (p.getManager().equals(manager)) {
                System.out.println(p);
            }
        }
    }
}
