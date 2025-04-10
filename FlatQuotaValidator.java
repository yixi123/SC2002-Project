public class FlatQuotaValidator implements IFlatQuotaValidator {
    @Override
    public boolean isFlatAvailable(Project project, FlatType flatType) {
        switch (flatType) {
            case TWO_ROOM:
                return project.getTwoRoomUnits() > 0;
            case THREE_ROOM:
                return project.getThreeRoomUnits() > 0;
            default:
                return false;
        }
    }
}