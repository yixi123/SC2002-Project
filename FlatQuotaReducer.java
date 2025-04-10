public class FlatQuotaReducer implements IFlatQuotaReducer {
    @Override
    public void reduceFlatQuota(Project project, FlatType flatType) {
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