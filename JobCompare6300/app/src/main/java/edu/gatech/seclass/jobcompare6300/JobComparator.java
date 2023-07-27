package edu.gatech.seclass.jobcompare6300;

public final class JobComparator {
    public static Integer compareJobs(Job job1, Job job2, ComparisonSettings comparisonSettings) {
        double score1 = JobScorer.calculateScore(job1, comparisonSettings);
        double score2 = JobScorer.calculateScore(job2, comparisonSettings);
        if(score1 == score2) {
            return 0;
        } else if(score1 > score2) {
            return -1;
        }
        return 1;
    }
}
