import java.time.LocalDate;

public class ActiveProjectResolver implements IActiveProjectResolver {
    public static Project getCurrentProject(HDBManager manager) {
        LocalDate today = LocalDate.now();

        for (Project p : ProjectDB.getAllProjects()) {
            if (p.getManager().equals(manager)
                && p.isVisible()
                && !today.isBefore(p.getOpeningDate())
                && !today.isAfter(p.getClosingDate())) {
                return p;
            }
        }

        return null;
    }
}