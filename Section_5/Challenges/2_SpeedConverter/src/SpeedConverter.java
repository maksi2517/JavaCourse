public class SpeedConverter {
    public double toMilesPerHour (double kilometersPerHour) {
        if (kilometersPerHour < 0) {
            return -1;
        } else  {
            double milesPerHour = kilometersPerHour * 1.609;
            return Math.round(milesPerHour);
        }
    }
}
