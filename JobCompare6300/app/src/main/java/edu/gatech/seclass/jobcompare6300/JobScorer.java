package edu.gatech.seclass.jobcompare6300;

public final class JobScorer {
    public static double calculateScore(Job job, ComparisonSettings comparisonSettings){
        double ays = (double) job.getYearlySalary() / job.getLocation().getCostOfLiving();
        double ayb = (double) job.getYearlyBonus() / job.getLocation().getCostOfLiving();
        double lt = job.getLeave();
        double mpl = job.getParentalLeave();
        double li = job.getLifeInsurance();
        return (((double) comparisonSettings.getYearlySalary() * ays)
                + ((double) comparisonSettings.getYearlyBonus() * ayb)
                + ((double) comparisonSettings.getLeave() * lt * ays /260)
                + ((double) comparisonSettings.getPaternalLeave() * mpl*ays/260)
                + ((double) comparisonSettings.getLifeInsurance() * li/100*ays)) /
                ((double) comparisonSettings.getYearlySalary() + (double) comparisonSettings.getYearlyBonus()
                + (double) comparisonSettings.getLeave() + (double) comparisonSettings.getPaternalLeave()
                + (double) comparisonSettings.getLifeInsurance());
    }
}
