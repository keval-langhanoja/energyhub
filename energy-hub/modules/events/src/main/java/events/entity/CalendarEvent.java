package events.entity;

import java.util.List;

public class CalendarEvent {
    private String eventName;
    private String eventDescription;
    private String eventType;
    private String eventImageURL;
    private String meetingLink;
    private String otherLinks;
    private List<String> speakers;
    private String startDate;
    private String endDate;
    private String attendance;
    private long eventId;
    private long resourcePrimaryKey;
    private String eventHostName;
    private String eventHostRole;
    private String eventHostLink;
    private String userImageURL;
    private String startDateDisplay;
    private String startTime;
    private String endTime;
    private String attendees;
    private String attendeesNames;
    private int attendeesTotal;
    private String detailURL;
    private String color;
    private boolean isLive;
    private boolean isUpcoming;
    private boolean ended;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public boolean isUpcoming() {
        return isUpcoming;
    }

    public void setUpcoming(boolean upcoming) {
        isUpcoming = upcoming;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventImageURL() {
        return eventImageURL;
    }

    public void setEventImageURL(String eventImageURL) {
        this.eventImageURL = eventImageURL;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getOtherLinks() {
        return otherLinks;
    }

    public void setOtherLinks(String otherLinks) {
        this.otherLinks = otherLinks;
    }

    public List<String> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<String> speakers) {
        this.speakers = speakers;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getResourcePrimaryKey() {
        return resourcePrimaryKey;
    }

    public void setResourcePrimaryKey(long resourcePrimaryKey) {
        this.resourcePrimaryKey = resourcePrimaryKey;
    }

    public String getEventHostName() {
        return eventHostName;
    }

    public void setEventHostName(String eventHostName) {
        this.eventHostName = eventHostName;
    }

    public String getEventHostRole() {
        return eventHostRole;
    }

    public void setEventHostRole(String eventHostRole) {
        this.eventHostRole = eventHostRole;
    }

    public String getEventHostLink() {
        return eventHostLink;
    }

    public void setEventHostLink(String eventHostLink) {
        this.eventHostLink = eventHostLink;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getStartDateDisplay() {
        return startDateDisplay;
    }

    public void setStartDateDisplay(String startDateDisplay) {
        this.startDateDisplay = startDateDisplay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAttendees() {
        return attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public String getAttendeesNames() {
        return attendeesNames;
    }

    public void setAttendeesNames(String attendeesNames) {
        this.attendeesNames = attendeesNames;
    }

    public int getAttendeesTotal() {
        return attendeesTotal;
    }

    public void setAttendeesTotal(int attendeesTotal) {
        this.attendeesTotal = attendeesTotal;
    }

    public String getDetailURL() {
        return detailURL;
    }

    public void setDetailURL(String detailURL) {
        this.detailURL = detailURL;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
