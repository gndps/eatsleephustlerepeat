package io.github.gndps.eatsleephustlerepeat;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Gundeep on 03/11/17.
 */

public class RealmUtil {

    public static final String TAG = RealmUtil.class.getSimpleName();

    public static Realm getInstance() {
        Realm realm = Realm.getDefaultInstance();
        return realm;
    }

    public static void justSelected(ThingType thingType) {
        //set the end time of last selected thing
        endTheLastEntry();

        //create a new entry with the current time as the starting time
        createNewEntry(thingType);
    }

    private static void createNewEntry(ThingType thingType) {
        Realm realm = getInstance();
        try {
            realm.beginTransaction();
            Number maxId = realm.where(TaskEntry.class).max("id");
            if(maxId == null) {
                maxId = 0;
            }
            int nextId = maxId.intValue() + 1;
            TaskEntry newEntry = realm.createObject(TaskEntry.class, nextId);
            newEntry.setDate(getDateWithoutTime());
            newEntry.setStartTime(new Date());
            newEntry.setTaskName(getStringFromThingType(thingType));
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "unable to end the last entry", e);
        } finally {
            realm.close();
        }
    }

    public static Date getDateWithoutTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
            return dateWithoutTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void endTheLastEntry() {
        Realm realm = getInstance();
        try {
            realm.beginTransaction();
            Number maxId = realm.where(TaskEntry.class).max("id");
            if(maxId == null) {
                realm.cancelTransaction();
                return;
            }
            TaskEntry taskEntries = realm.where(TaskEntry.class).equalTo("id", maxId.intValue()).findFirst();
            if (taskEntries != null) {
                taskEntries.setEndTime(new Date());
                realm.commitTransaction();
            } else {
                realm.cancelTransaction();
            }
        } catch (Exception e) {
            Log.e(TAG, "unable to end the last entry", e);
        } finally {
            realm.close();
        }
    }

    public static ThingType getLastSelectedThingType() {
        Realm realm = getInstance();
        try {
            Number maxId = realm.where(TaskEntry.class).max("id");
            if(maxId == null) {
                return null;
            }
            TaskEntry taskEntries = realm.where(TaskEntry.class).equalTo("id", maxId.intValue()).findFirst();
            return getThingType(taskEntries.getTaskName());
        } catch (Exception ex) {
            Log.e(TAG, "some problem in getting the last selected task", ex);
            return null;
        } finally {
            realm.close();
        }
    }

    public static String getStringFromThingType(ThingType thingType) {
        switch (thingType) {
            case EAT:
                return "eat";
            case SLEEP:
                return "sleep";
            case WORK:
                return "work";
            case HUSTLE:
                return "hustle";
            case BREAK:
                return "break";
            default:
                return null;
        }
    }

    public static ThingType getThingType(String thingType) {
        switch (thingType) {
            case "eat":
                return ThingType.EAT;
            case "sleep":
                return ThingType.SLEEP;
            case "work":
                return ThingType.WORK;
            case "hustle":
                return ThingType.HUSTLE;
            case "break":
                return ThingType.BREAK;
            default:
                return null;
        }
    }

    public static String getHoursSpent(String eat, Date date) {
        try {
            Realm realm = getInstance();
            RealmResults<TaskEntry> results = realm.where(TaskEntry.class)
                    .equalTo("date", date)
                    .equalTo("taskName", eat)
                    .findAll();
            long totalDifference = 0;
            for (TaskEntry task : results) {
                long difference;
                if (task.getStartTime() != null && task.getEndTime() != null) {
                    difference = task.getEndTime().getTime() - task.getStartTime().getTime();
                } else {
                    // if end time is null, then this task is currently on
                    difference = new Date().getTime() - task.getStartTime().getTime();
                }
                totalDifference += difference;
            }
            return getHumanReadableTime((int) (totalDifference / (60 * 1000)));
        } catch (Exception e) {
            return "error";
        }
    }

    public static String getHumanReadableTime(int minutes) {
        if(minutes < 60) {
            return String.format("%d mins", minutes);
        } else if(minutes < 1440) { //1 day = 1440 minutes
            return String.format("%d hrs, %d mins", minutes/60, minutes%60);
        } else {
            return String.format("%d days", minutes / 1440);
        }
    }
}
