package com.cw.notas;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.TimeZone;

/**
 * A helper class for setting events in the devices calendar
 */
public class CalendarHelper {

    /**
     * Gets the id of the calendar on the device
     * @param context
     * @return ID of the calendar
     */
    /*
    * This Method is from this forum :
    * https://stackoverflow.com/questions/16242472/retrieve-the-default-calendar-id-in-android
    * By Malwinder Singh
    * */
    public static Long getCalendarId(Context context) {
        String[] projection = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        };
        Cursor calCursor = context.getContentResolver().query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
                null,
                CalendarContract.Calendars._ID + " ASC"
        );
        if (calCursor != null && calCursor.getCount() <= 0) {
            calCursor.close();
            calCursor = context.getContentResolver().query(
                    CalendarContract.Calendars.CONTENT_URI,
                    projection,
                    CalendarContract.Calendars.VISIBLE + " = 1",
                    null,
                    CalendarContract.Calendars._ID + " ASC"
            );
        }
        if (calCursor != null) {
            if (calCursor.moveToFirst()) {
                String calName;
                String calID;
                int nameCol = calCursor.getColumnIndex(projection[1]);
                int idCol = calCursor.getColumnIndex(projection[0]);

                calName = calCursor.getString(nameCol);
                calID = calCursor.getString(idCol);
                calCursor.close();
                return Long.parseLong(calID);
            }
            calCursor.close();
        }
        return null;
    }


    /**
     * Sets an event in the calendar on the device
     * @param context
     * @param title
     * @param description
     * @param startTime
     * @param endTime
     * @param calId
     */
    public static void setEvent (Context context,String title, String description, long startTime, long endTime, long calId) {

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.DTSTART, startTime);
        values.put(CalendarContract.Events.DTEND, endTime);
        values.put(CalendarContract.Events.CALENDAR_ID, calId); // ID of the calendar to add the event to
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        ContentResolver cr = context.getContentResolver();
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

        // Retrieve the ID of the new event
        long eventID = Long.parseLong(uri.getLastPathSegment());

        // Add a reminder for the event
        ContentValues reminderValues = new ContentValues();
        reminderValues.put(CalendarContract.Reminders.MINUTES, 15);
        reminderValues.put(CalendarContract.Reminders.EVENT_ID, eventID);
        reminderValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri reminderUri = cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues);

    }
}
