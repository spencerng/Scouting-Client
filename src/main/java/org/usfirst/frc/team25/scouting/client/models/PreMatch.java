package org.usfirst.frc.team25.scouting.client.models;


/** PreMatch object model to deserialize JSON data. 
 *  Modify as necessary for each season.
 */
public class PreMatch {

    public PreMatch(String scoutName, String currentEvent, String scoutPos, int matchNum, int teamNum) {
        this.scoutName = scoutName;
        this.currentEvent = currentEvent;
        this.scoutPos = scoutPos;
        this.matchNum = matchNum;
        this.teamNum = teamNum;
    }

    String scoutName, currentEvent, scoutPos;
    int matchNum, teamNum;



    public String getScoutName() {
        return scoutName;
    }

    public String getCurrentEvent() {
        return currentEvent;
    }

    public String getScoutPos() {
        return scoutPos;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public PreMatch(){
        //Default empty constructor for JSON parsing
    }
}
