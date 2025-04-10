public class OwnProjectViewer implements IOwnProjectViewer {
    @Override
    public void viewOwnProjects() {
        HDBManager manager = (HDBManager) LoginSession.getUser();
        for (Project p : ProjectDB.getAllProjects()) {
            if (p.getManager().equals(manager)) {
                System.out.println(p);
            }
        }
    }
}