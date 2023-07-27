package edu.gatech.seclass.jobcompare6300;

public class ComparisonSettings {

    private int yearlySalary;
    private int yearlyBonus;
    private int leave;
    private int paternalLeave;
    private int lifeInsurance;

    public int getYearlySalary() {
        return yearlySalary;
    }

    public int getYearlyBonus() {
        return yearlyBonus;
    }

    public int getLeave() {
        return leave;
    }

    @Override
    public String toString() {
        return "ComparisonSettings{" +
                "yearlySalary=" + yearlySalary +
                ", yearlyBonus=" + yearlyBonus +
                ", leave=" + leave +
                ", paternalLeave=" + paternalLeave +
                ", lifeInsurance=" + lifeInsurance +
                '}';
    }

    public void setYearlySalary(int yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public void setYearlyBonus(int yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public ComparisonSettings(int yearlySalary, int yearlyBonus, int leave, int paternalLeave, int lifeInsurance) {
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.leave = leave;
        this.paternalLeave = paternalLeave;
        this.lifeInsurance = lifeInsurance;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public void setPaternalLeave(int paternalLeave) {
        this.paternalLeave = paternalLeave;
    }

    public void setLifeInsurance(int lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public int getPaternalLeave() {
        return paternalLeave;
    }

    public int getLifeInsurance() {
        return lifeInsurance;
    }
}
