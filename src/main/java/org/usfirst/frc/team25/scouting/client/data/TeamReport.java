package org.usfirst.frc.team25.scouting.client.data;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Object model containing individual reports of teams in events and methods to process data
 *
 * @author sng
 */
public class TeamReport {

    final transient ArrayList<ScoutEntry> entries;

    final int teamNum; //transient because it's the key of the HashMap in EventReport
    final String autoGearPegLoc = "";
    final transient String frequentPilotCommentStr = "";
    final ArrayList<Integer> teleOpCubes = new ArrayList<>();
    private final ArrayList<Integer> totalSwitchCubes = new ArrayList<>();
    private final ArrayList<Integer> totalScaleCubes = new ArrayList<>();
    private final ArrayList<Integer> totalCubes = new ArrayList<>();
    private final ArrayList<Integer> totalDroppedCubes = new ArrayList<>();
    private final ArrayList<Double> firstCubeTimes = new ArrayList<>();
    String teamName;
    double avgPointsPerCycle, avgCycles, sdCycles, autoRunPercentage,
            avgHighGoals, avgLowGoals, sdHighGoals, sdLowGoals, sdPointsPerCycle;
    double avgAutoScore;
    double avgTeleOpScore;
    double avgMatchScore;
    double avgAutoKpa;

    //Instance variables below should not be serialized but may be accessed by EventReports for analysis
    double avgTeleOpKpa;
    double avgAutoGears;
    double avgTeleOpGears;
    double avgHoppers;
    double sdAutoScore;
    double sdTeleOpScore;
    double sdMatchScore;
    double sdAutoKpa;
    double sdTeleOpKpa;
    double sdAutoGears;
    double sdTeleOpGears;
    double takeoffAttemptPercentage, takeoffAttemptSuccessPercentage, takeoffPercentage;// attempt is out of all matches; success is for each attempt
    double pilotPlayPercentage;
    double autoGearAttemptSuccessPercent, leftPegPercent, centerPegPercent, rightPegPercent, avgDroppedGears;
    boolean hasPickup, hasIntake, isActive, doNotPick;
    double avgTeleOpKpaFuelFocus, avgTeleOpGearsGearFocus, fuelFocusPercent, gearFocusPercent;
    transient String frequentRobotCommentStr = "";
    transient String allComments;
    int totalTakeoffAttempts;
    int totalTakeoffSuccesses;
    int totalReachBaseline;
    int totalAutoGearAttempt, totalAutoGearSuccess, totalLeftPegAttempt, totalLeftPegSuccess, totalCenterPegAttempt, totalCenterPegSuccess,
            totalRightPegAttempt, totalRightPegSuccess;
    private int totalPilotPlaying;

    public TeamReport(int teamNum) {
        this.teamNum = teamNum;
        entries = new ArrayList<>();
    }

    /**
     * Method to fetch the nickname of a team from a file
     *
     * @param dataLocation location of the TeamNameList file generated by <code>exportTeamList</code>
     */
    public void autoGetTeamName(File dataLocation) {
        String data = FileManager.getFileString(dataLocation);
        String[] values = data.split(",\n");

        for (String value : values) {
            if (value.split(",")[0].equals(Integer.toString(teamNum))) {

                teamName = value.split(",")[1];
                return; //Terminates the method
            }
        }
    }

    private void calculateTotals() {

        totalTakeoffAttempts = totalTakeoffSuccesses = 0;
        int totalAutoShootsKey;
        totalPilotPlaying = totalReachBaseline = totalAutoShootsKey = 0;
        totalAutoGearSuccess = totalAutoGearAttempt = 0;
        totalLeftPegAttempt = totalLeftPegSuccess = totalCenterPegAttempt = totalCenterPegSuccess = 0;
        totalRightPegSuccess = totalRightPegAttempt = 0;
		
		
		/*for(ScoutEntry entry : entries){
			if(entry.getTeleOp().isAttemptTakeoff())
				totalTakeoffAttempts++;
			if(entry.getTeleOp().isReadyTakeoff())
				totalTakeoffSuccesses++;
			
			if(entry.getPreMatch().isPilotPlaying())
				totalPilotPlaying++;
			if(entry.auto.isBaselineCrossed())
				totalReachBaseline++;
			/*if(entry.getAuto().isShootsFromKey())
				totalAutoShootsKey++;*\/
			
			if(entry.auto.isAttemptGear()){
				totalAutoGearAttempt++;
				if(entry.auto.getGearPeg().equals("Left"))
					totalLeftPegAttempt++;
				if(entry.auto.getGearPeg().equals("Center"))
					totalCenterPegAttempt++;
				if(entry.auto.getGearPeg().equals("Right"))
					totalRightPegAttempt++;
				
			}
			
			if(entry.auto.isSuccessGear()){
				totalAutoGearSuccess++;
				if(entry.auto.getGearPeg().equals("Left"))
					totalLeftPegSuccess++;
				if(entry.auto.getGearPeg().equals("Center"))
					totalCenterPegSuccess++;
				if(entry.auto.getGearPeg().equals("Right"))
					totalRightPegSuccess++;
				
			}
			
			
			totalSwitchCubes.add((entry.auto.isUseHoppers() ? 1 : 0)
					+ entry.teleOp.getHopppersUsed());
			totalScaleCubes.add(entry.auto.getHighGoals()+entry.auto.getLowGoals()
					+entry.teleOp.getHighGoals()+entry.teleOp.getLowGoals());
			autoGears.add(entry.getAuto().isSuccessGear()? 1 : 0);
			teleOpCubes.add(entry.getTeleOp().getGearsDelivered());
			teleOpKpa.add(entry.teleOpKpa);
			autoKpas.add(entry.autoKpa);
			autoScores.add(entry.autoScore);
			teleOpScores.add( entry.teleScore);
			matchScores.add(entry.totalScore);
			totalPointsPerCycle.add(entry.pointsPerCycle);
			totalCycles.add(entry.teleOp.getNumCycles());
			totalDroppedCubes.add(entry.teleOp.getGearsDropped());
			
			totalHighGoals.add( entry.getAuto().getHighGoals()+entry.getTeleOp().getHighGoals());
			totalLowGoals.add(entry.getAuto().getLowGoals()+entry.getTeleOp().getLowGoals());
			
			if(entry.postMatch.getFocus().equals("Gears"))
				totalTeleOpGearsGearFocus.add(entry.teleOp.getGearsDelivered());
			if(entry.postMatch.getFocus().equals("Fuel"))
				firstCubeTimes.add(entry.teleOpKpa);
		}*/


    }

    public void calculateStats() {

        calculateTotals();

        takeoffAttemptPercentage = ((double) totalTakeoffAttempts) / entries.size() * 100; //how often they attempt
        if (totalTakeoffAttempts == 0)
            takeoffAttemptSuccessPercentage = 0;
        else
            takeoffAttemptSuccessPercentage = ((double) totalTakeoffSuccesses) / totalTakeoffAttempts * 100; //percentage for all attempts, "consistency"


        takeoffPercentage = ((double) totalTakeoffSuccesses) / entries.size() * 100; //percentage for all matches


        avgHoppers = Statistics.average(Statistics.toDoubleArrayList(totalSwitchCubes));


        double avgTotalFuel = Statistics.average(Statistics.toDoubleArrayList(totalScaleCubes));
        double sdTotalFuel = Statistics.popStandardDeviation(Statistics.toDoubleArrayList(totalScaleCubes));


        avgTeleOpGears = Statistics.average(Statistics.toDoubleArrayList(teleOpCubes));
        sdTeleOpGears = Statistics.popStandardDeviation(Statistics.toDoubleArrayList(teleOpCubes));

        avgDroppedGears = Statistics.average(Statistics.toDoubleArrayList(totalDroppedCubes));


        avgAutoGears = Statistics.average(Statistics.toDoubleArrayList(totalCubes));
        sdAutoGears = Statistics.popStandardDeviation(Statistics.toDoubleArrayList(totalCubes));


        autoGearAttemptSuccessPercent = totalAutoGearAttempt != 0 ? (double) totalAutoGearSuccess / totalAutoGearAttempt * 100 : 0;
        leftPegPercent = totalLeftPegAttempt != 0 ? (double) totalLeftPegSuccess / totalLeftPegAttempt * 100 : 0;
        rightPegPercent = totalRightPegAttempt != 0 ? (double) totalRightPegSuccess / totalRightPegAttempt * 100 : 0;
        centerPegPercent = totalCenterPegAttempt != 0 ? (double) totalCenterPegSuccess / totalCenterPegAttempt * 100 : 0;

        pilotPlayPercentage = ((double) totalPilotPlaying) / entries.size() * 100;



        autoRunPercentage = totalReachBaseline / ((double) entries.size()) * 100;


        avgTeleOpKpaFuelFocus = Statistics.average(firstCubeTimes);

        fuelFocusPercent = (double) firstCubeTimes.size() / entries.size() * 100;
		
		/*if(totalAutoShootsKey/((double)entries.size())>=0.50)
			autoShootsKey = true;
		else autoShootsKey = false;*/

        HashMap<String, Integer> commentFrequencies = new HashMap<>();


        for (String key : entries.get(0).getPostMatch().getRobotQuickCommentSelections().keySet()) {
            commentFrequencies.put(key, 0);
            for (ScoutEntry entry : entries)
                if (entry.getPostMatch().getRobotQuickCommentSelections().get(key))
                    commentFrequencies.put(key, 1 + commentFrequencies.get(key));
        }

        ArrayList<String> frequentRobotComment = new ArrayList<>();

        for (String key : commentFrequencies.keySet())
            if (commentFrequencies.get(key) >= entries.size() / 4.0)
                frequentRobotComment.add(key);


        doNotPick = frequentRobotComment.contains("Do not pick (explain)");
        isActive = frequentRobotComment.contains("Active gear mech.");
        hasIntake = frequentRobotComment.contains("Fuel intake");
        hasPickup = frequentRobotComment.contains("Gear pickup");

        frequentRobotComment.remove("Do not pick (explain)");
        frequentRobotComment.remove("Active gear mech.");
        frequentRobotComment.remove("Fuel intake");
        frequentRobotComment.remove("Gear pickup");

        commentFrequencies = new HashMap<>();


        for (String comment : frequentRobotComment)
            frequentRobotCommentStr += removeCommas(comment) + ';';

        computeRankingMetrics();

        allComments = "";
        for (ScoutEntry entry : entries) {
            if (!entry.getPostMatch().getRobotComment().equals(""))
                allComments += entry.getPostMatch().getRobotComment() + "; ";
			/*if(!autoGearPegLoc.contains(entry.getAuto().getGearPeg()))
				autoGearPegLoc+=entry.getAuto().getGearPeg()+"; ";*/
        }


    }

    private String removeCommas(String s) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ',')
                newString.append(s.charAt(i));
            else newString.append("; ");
        }
        return newString.toString();
    }

    private void computeRankingMetrics() {
        double autoAbility = 0;
        double teleOpAbility = 0;
        double driveTeamAbility = 0;
        double robotQualities = 0;
        double firstPickAbility = 0;
        double secondPickAbility = 0;
    }

    public int getTeamNum() {
        return teamNum;
    }

    private String removeCommasAndBreaks(String s) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ',' && s.charAt(i) != '\n')
                newString.append(s.charAt(i));
            else newString.append("; ");
        }
        return newString.toString();
    }


    public void addEntry(ScoutEntry entry) {
        entry.getPostMatch().setRobotComment(removeCommasAndBreaks(entry.getPostMatch().getRobotComment()));

        entries.add(entry);
    }


}
