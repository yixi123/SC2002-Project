public class FlatQuotaManager implements IFlatQuotaManager {

    @Override
    public boolean isQuotaAvailable(Project project, FlatType flatType) {
        switch (flatType) {
            case TWO_ROOM:
                return project.getTwoRoomUnits() > 0;
            case THREE_ROOM:
                return project.getThreeRoomUnits() > 0;
            default:
                return false;
        }
    }

    @Override
    public void reduceQuota(Project project, FlatType flatType) {
        switch (flatType) {
            case TWO_ROOM:
                project.setTwoRoomUnits(project.getTwoRoomUnits() - 1);
                break;
            case THREE_ROOM:
                project.setThreeRoomUnits(project.getThreeRoomUnits() - 1);
                break;
        }
    }
}
