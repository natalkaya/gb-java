package lesson1;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.Arrays;

public enum DayOfWeek {
    MONDAY(0),
    THUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5, 5),
    SUNDAY(6, 0);

    private final int numberOfWeek;
    private final int workingHours;

    DayOfWeek(int numberOfWeek, int workingHours) {
        this.numberOfWeek = numberOfWeek;
        this.workingHours = workingHours;
    }

    DayOfWeek(int numberOfWeek) {
        this.numberOfWeek = numberOfWeek;
        this.workingHours = 8;
    }

    public static DayOfWeek valueOf(int numberOfWeek) throws NotFound {
        return Arrays.stream(DayOfWeek.values()).filter(dayOfWeek ->
                dayOfWeek.numberOfWeek == numberOfWeek).findFirst().orElseThrow(NotFound::new);
    }

    public static int getWorkingHours(DayOfWeek dayOfWeek) {
        if (dayOfWeek.equals(SUNDAY)) System.out.println("Non working day!");
        int hours = 0;
        int currentDayNumber = dayOfWeek.numberOfWeek;

        while (currentDayNumber != SUNDAY.numberOfWeek) {
            try {
                hours += valueOf(currentDayNumber).workingHours;
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
            currentDayNumber++;
        }

        return hours;

    }

}
