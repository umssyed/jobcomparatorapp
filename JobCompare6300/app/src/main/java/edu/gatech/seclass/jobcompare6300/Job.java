package edu.gatech.seclass.jobcompare6300;

public class Job {
    // Variables
    private String title;
    private String company;
    private Location location;
    private int yearlySalary;
    private int yearlyBonus;
    private int leave;
    private int parentalLeave;
    private int lifeInsurance;

    public Job () {
        this.title = "";
        this.company = "";
        this.location = new Location("", "",1);
        this.yearlySalary = 0;
        this.yearlyBonus = 0;
        this.leave = 0;
        this.parentalLeave = 0;
        this.lifeInsurance = 0;
    }
    public Job (String title, String company, Location location,
                int yearlySalary, int yearlyBonus, int leave,
                int parentalLeave, int lifeInsurance) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.leave = leave;
        this.parentalLeave = parentalLeave;
        this.lifeInsurance = lifeInsurance;
    };

    public Job (String title, String company, Location location) {
        this(title, company, location, 0, 0, 0, 0, 0);
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public Location getLocation() {
        return location;
    }

    public int getYearlySalary() {
        return yearlySalary;
    }

    public int getYearlyBonus() {
        return yearlyBonus;
    }

    public int getLeave() {
        return leave;
    }

    public int getParentalLeave() {
        return parentalLeave;
    }

    public int getLifeInsurance() {
        return lifeInsurance;
    }

    @Override
    public String toString() {
        return "Job{" +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", yearlySalary=" + yearlySalary +
                ", yearlyBonus=" + yearlyBonus +
                ", leave=" + leave +
                ", parentalLeave=" + parentalLeave +
                ", lifeInsurance=" + lifeInsurance +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setYearlySalary(int yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public void setYearlyBonus(int yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public void setParentalLeave(int parentalLeave) {
        this.parentalLeave = parentalLeave;
    }

    public void setLifeInsurance(int lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }
}
