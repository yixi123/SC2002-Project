public interface IFlatQuotaManager {
    /**
     * Checks whether a flat unit of the given type is available in the project.
     *
     * @param project  the project to check
     * @param flatType the flat type (e.g. TWO_ROOM, THREE_ROOM)
     * @return true if quota is available, false otherwise
     */
    boolean isQuotaAvailable(Project project, FlatType flatType);

    /**
     * Reduces the available quota for the given flat type in the project.
     *
     * @param project  the project to update
     * @param flatType the flat type whose count should be reduced
     */
    void reduceQuota(Project project, FlatType flatType);
}
