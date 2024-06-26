package events.commands.render;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import event.attendance.model.EventAttendance;
import event.attendance.service.EventAttendanceLocalServiceUtil;
import events.constants.EventsPortletKeys;
import events.entity.CalendarEvent;
import events.helper.helper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component(immediate = true, property = {"javax.portlet.name=" + EventsPortletKeys.EVENTS,
        "mvc.command.name=/"}, service = MVCRenderCommand.class)
public class EventMVCRenderCommand implements MVCRenderCommand {

    private static final String EVENT_CATEGORY_NAME = "Events";
    private static final String EVENT_STRUCTURE_NAME = "event";
    private static final String EVENT_NAME = "Text96793541";
    private static final String EVENT_DESCRIPTION = "RichText21467088";
    private static final String ATTENDANCE = "Select14676458";
    private static final String MEETING_LINK = "Text13371686";
    private static final String EVENT_IMAGE = "Image78215844";
    private static final String EVENT_TYPE = "Select88479481";
    private static final String OTHER_LINKS = "Text84052150";
    private static final String START_DATE = "Text38676037";
    private static final String END_DATE = "Text61342977";
    private static final String SPEAKERS = "Text39318031";
    private static final String WEBINAR = "Option35284515";
    private static final String CONFERENCE = "Option47047093";
    private static final String CALL_FOR_APPLICATIONS = "Option35398826";
    private static final String WORKSHOP = "Option22110287";
    private static final String OTHER_TYPE = "Option55959665";

    private static final Log log = LogFactoryUtil.getLog(EventMVCRenderCommand.class);

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
        DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructures().stream()
                .filter(st -> st.getName("en_US").equalsIgnoreCase(EVENT_STRUCTURE_NAME)).findFirst().orElse(null);
        List<JournalArticle> eventsList = new ArrayList<>();
        List<JournalArticle> similarTopics = new ArrayList<>();
        try {
            String eventId = httpReq.getParameter("articleId");
            int pageNo = Integer.parseInt(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
            int pageSize = Integer.parseInt(helper.ifEmpty(httpReq.getParameter("pageSize"), "9"));
            JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));

            AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
                    .filter(category -> category.getName().equalsIgnoreCase(EVENT_CATEGORY_NAME)).findFirst().orElse(null);
            renderRequest.setAttribute("addEvent", true);
            AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
            if (Validator.isNotNull(assetCategory))
                assetEntryQuery.setAllCategoryIds(new long[]{assetCategory.getCategoryId()});
            assetEntryQuery.setOrderByCol1("createDate");

            if (themeDisplay.getURLCurrent().contains("events") || themeDisplay.getURLCurrent().contains("allEvents")) {
                if (themeDisplay.getURLCurrent().contains("event")) {
                    assetEntryQuery.setStart(0);
                    assetEntryQuery.setEnd(6);
                }
                List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
                for (AssetEntry ae : assetEntryList) {
                    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
                    JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
                    if (Validator.isNotNull(ddmStructure) && journalArticle.getDDMStructureKey().equals(ddmStructure.getStructureKey()))
                        eventsList.add(journalArticle);
                }

                if (filter.has("type") && !filter.getJSONArray("type").isEmpty()) {
                    eventsList = getFilteredArticles(themeDisplay, eventsList, filter.getJSONArray("type"), EVENT_TYPE);
                }

                if (filter.has("attendance") && !filter.getJSONArray("attendance").isEmpty()) {
                    eventsList = getFilteredArticles(themeDisplay, eventsList, filter.getJSONArray("attendance"), ATTENDANCE);
                }

                if (filter.has("status") && !filter.getJSONArray("status").isEmpty()) {
                    eventsList = getFilteredArticlesByStatus(eventsList, themeDisplay);
                }

                if (!themeDisplay.getURLCurrent().contains("allEvents")) {
                    eventsList = getFilteredArticlesByStatus(eventsList, themeDisplay);
                    eventsList = getPageLimit(eventsList, 0, 5);
                } else {
                    eventsList = getPageLimit(eventsList, pageNo, pageSize);
                }
            } else if (!helper.isEmpty(eventId)) {
                JournalArticle event = JournalArticleLocalServiceUtil.fetchLatestArticle(themeDisplay.getScopeGroupId(), eventId, WorkflowConstants.STATUS_APPROVED);
                if (event != null) {
                    eventsList.add(event);
                }
                assert event != null;
                String content = event.getContentByLocale(themeDisplay.getLanguageId());
                Document document = SAXReaderUtil.read(content);
                String type = document.selectSingleNode("/root/dynamic-element[@name='" + EVENT_TYPE + "']").hasContent() ?
                        document.selectSingleNode("/root/dynamic-element[@name='" + EVENT_TYPE + "']/dynamic-content").getText() : "";
                assetEntryQuery.setStart(0);
                assetEntryQuery.setEnd(5);
                List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
                for (AssetEntry ae : assetEntryList) {
                    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
                    JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
                    similarTopics.add(journalArticle);
                }
                if (filter.has("type") && !filter.getJSONArray("type").isEmpty()) {
                    similarTopics = getFilteredArticles(themeDisplay, similarTopics, new JSONArray().put(type), EVENT_TYPE);
                }
                renderRequest.setAttribute("similarTopics", getEventData(similarTopics, themeDisplay, renderRequest));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        try {
            return pageRedirection(themeDisplay, renderRequest, eventsList, ddmStructure);
        } catch (PortalException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public static String pageRedirection(ThemeDisplay themeDisplay, RenderRequest renderRequest, List<JournalArticle> eventsList, DDMStructure structure) throws PortalException, DocumentException {
        HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
        int pageNo = Integer.parseInt(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
        int pageSize = Integer.parseInt(helper.ifEmpty(httpReq.getParameter("pageSize"), "9"));

        List<CalendarEvent> calendarEvents = getEventData(eventsList, themeDisplay, renderRequest);
        renderRequest.setAttribute("eventsList", calendarEvents);
        renderRequest.setAttribute("is_signed_in", themeDisplay.isSignedIn());
        if (themeDisplay.getURLCurrent().contains("allEvents")) {
            getFilterData(renderRequest, structure);
            int totalPages = eventsList.isEmpty() ? 0 : (int) (double) (eventsList.size() / pageSize);

            renderRequest.setAttribute("totalPages", totalPages);
            renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
            renderRequest.setAttribute("pageSize", pageSize);

            List<User> users = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
            List<User> approvedUsers = new ArrayList<>();
            for (User u : users) {
                u.setComments(u.getPortraitURL(themeDisplay));
                approvedUsers.add(u);
            }
            renderRequest.setAttribute("allUsers", approvedUsers);
            String eventsJson = getEventCalendarData(calendarEvents);
            renderRequest.setAttribute("eventsJson", eventsJson);
            return "/CardsView.jsp";
        } else if (themeDisplay.getURLCurrent().contains("event-detail")) {
            return "/event.jsp";
        } else {
            getFilterData(renderRequest, structure);
            return "/view.jsp";
        }
    }

    private static String getEventCalendarData(List<CalendarEvent> calendarEvents) {
        JSONArray res = new JSONArray();
        for (CalendarEvent calendarEvent : calendarEvents) {
            JSONObject joRes = new JSONObject();
            joRes.put("title", calendarEvent.getEventName());
            joRes.put("start", calendarEvent.getStartDate());
            joRes.put("end", calendarEvent.getEndDate());
            joRes.put("allDay", false);
            joRes.put("color", calendarEvent.getColor());
            joRes.put("eventTypeName", calendarEvent.getEventType());
            joRes.put("detailURL", calendarEvent.getDetailURL());
            res.put(joRes);
        }
        return res.toString();
    }

    private static List<CalendarEvent> getEventData(List<JournalArticle> eventsList, ThemeDisplay themeDisplay, RenderRequest renderRequest) {
        List<CalendarEvent> calendarEvents = new ArrayList<>();
        try {
            for (JournalArticle event : eventsList) {
                CalendarEvent calendarEvent = new CalendarEvent();

                String eventName = helper.getDFromContentXmlString(event, EVENT_NAME, themeDisplay);
                String description = helper.getDFromContentXmlString(event, EVENT_DESCRIPTION, themeDisplay);
                String attendance = helper.getDFromContentXmlString(event, ATTENDANCE, themeDisplay);
                String type = helper.getDFromContentXmlString(event, EVENT_TYPE, themeDisplay);
                String meetingLink = helper.getDFromContentXmlString(event, MEETING_LINK, themeDisplay);
                String otherLinks = helper.getDFromContentXmlString(event, OTHER_LINKS, themeDisplay);
                String speakers = helper.getDFromContentXmlString(event, SPEAKERS, themeDisplay);
                String startDate = helper.getData(event, START_DATE, themeDisplay);
                String endDate = helper.getData(event, END_DATE, themeDisplay);

                User host = UserLocalServiceUtil.getUser(event.getUserId());

                String content = event.getContentByLocale(themeDisplay.getLanguageId());
                Document document = SAXReaderUtil.read(content);
                JSONObject coverImageJO = new JSONObject();
                if (document.selectSingleNode("/root/dynamic-element[@name='" + EVENT_IMAGE + "']") != null) {
                    coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='" + EVENT_IMAGE + "']").hasContent() ?
                            document.selectSingleNode("/root/dynamic-element[@name='" + EVENT_IMAGE + "']/dynamic-content").getText() : "{}");
                }
                String imageUrl = "";
                if (coverImageJO.has("fileEntryId")) {
                    DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));

                    if (image != null) {
                        imageUrl =
                                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
                                        image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
                                        System.currentTimeMillis();
                    }
                }

                calendarEvent.setEventId(event.getId());
                calendarEvent.setResourcePrimaryKey(event.getResourcePrimKey());
                calendarEvent.setEventHostName(host.getFullName());
                User user = UserLocalServiceUtil.getUser(event.getUserId());
                if (Validator.isNotNull(user)) {
                    List<Role> roles = user.getRoles();
                    if (!roles.isEmpty()) {
                        Role role = roles.get(0);
                        calendarEvent.setEventHostRole(role.getName());
                        calendarEvent.setEventHostLink("detail?userDetail&categName=" + role.getName() +
                                "&groupId=" + host.getGroupId() + "&articleId=" + host.getUserId());

                    }
                }

                calendarEvent.setUserImageURL(host.getPortraitURL(themeDisplay));
                calendarEvent.setEventName(eventName);

                calendarEvent.setEventDescription(description);
                calendarEvent.setEventImageURL(imageUrl);

                Date startDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(startDate);
                Date endDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(endDate);

                calendarEvent.setStartDateDisplay(new SimpleDateFormat("dd MMM yyyy HH:mm aa").format(startDateDate));
                calendarEvent.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startDateDate));
                calendarEvent.setEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endDateDate));
                calendarEvent.setStartTime(new SimpleDateFormat("HH:mm aa").format(startDateDate));
                calendarEvent.setEndTime(new SimpleDateFormat("HH:mm aa").format(endDateDate));

                calendarEvent.setAttendance(attendance);
                calendarEvent.setEventType(type);
                calendarEvent.setMeetingLink(meetingLink);
                calendarEvent.setOtherLinks(otherLinks);

                List<String> fullName = new ArrayList<>();
                if (!helper.isEmpty(speakers)) {
                    String[] speakersList = speakers.split(StringPool.COMMA);
                    fullName.addAll(Arrays.asList(speakersList));
                }
                calendarEvent.setSpeakers(fullName);

                List<EventAttendance> attendanceList = EventAttendanceLocalServiceUtil.getEventAttendances(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

                JSONArray attendees = new JSONArray(attendanceList);

                JSONArray userJA = new JSONArray();
                StringBuilder attendeesNames = new StringBuilder();
                for (int i = 0; i < attendees.length(); i++) {
                    JSONObject jo = attendees.getJSONObject(i);
                    User attendee = UserLocalServiceUtil.getUser(jo.getLong("userId"));
                    if (attendee != null) {
                        JSONObject uJO = new JSONObject();
                        uJO.put("FullName", attendee.getFullName());
                        uJO.put("PortraitUrl", attendee.getPortraitURL(themeDisplay));
                        if (i < 3)
                            attendeesNames.append(attendee.getFirstName()).append(", ");
                        userJA.put(uJO);
                    }
                }

                calendarEvent.setAttendance(userJA.toString());
                calendarEvent.setAttendeesNames(!helper.isEmpty(attendeesNames.toString()) ? attendeesNames.substring(0, attendeesNames.length() - 2) : "");
                calendarEvent.setAttendeesTotal(userJA.length());

                getEventStatus(calendarEvent, startDateDate, endDateDate);

                calendarEvent.setDetailURL("/event-detail?event&articleId=" + event.getArticleId());

                //Event Color
                if (type.equalsIgnoreCase(WEBINAR)) {
                    calendarEvent.setColor("#093A5D");
                    calendarEvent.setEventType("Webinar");
                }
                if (type.equalsIgnoreCase(CONFERENCE)) {
                    calendarEvent.setColor("#88c08c");
                    calendarEvent.setEventType("Conference");
                }
                if (type.equalsIgnoreCase(CALL_FOR_APPLICATIONS)) {
                    calendarEvent.setColor("#feca30");
                    calendarEvent.setEventType("CallforApplications");
                }
                if (type.equalsIgnoreCase(WORKSHOP)) {
                    calendarEvent.setColor("#23b6d052");
                    calendarEvent.setEventType("Workshop");
                }
                if (type.equalsIgnoreCase(OTHER_TYPE)) {
                    calendarEvent.setColor("#d3744b");
                    calendarEvent.setEventType("Other");
                }

                calendarEvents.add(calendarEvent);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return calendarEvents;
    }

    private static CalendarEvent getEventStatus(CalendarEvent calendarEvent, Date startDateDate, Date endDateDate) {
        boolean isLive = false;
        boolean isUpcoming = false;
        boolean ended = false;

        Instant instant = Instant.now();
        LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        LocalDateTime start = LocalDateTime.ofInstant(startDateDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(endDateDate.toInstant(), ZoneId.systemDefault());

        if (start.isBefore(now) && end.isBefore(now)) {
            ended = true;
        }
        if (start.isAfter(now) && end.isAfter(now)) {
            isUpcoming = true;
        }
        if (start.isBefore(now) && end.isAfter(now)) {
            isLive = true;
        }

        calendarEvent.setLive(isLive);
        calendarEvent.setUpcoming(isUpcoming);
        calendarEvent.setEnded(ended);
        return calendarEvent;
    }

    public static void getFilterData(RenderRequest renderRequest,
                                     com.liferay.dynamic.data.mapping.model.DDMStructure struct) {

        JSONObject jo = new JSONObject(struct.getDefinition());
        JSONArray ja = new JSONArray(jo.get("fields").toString());

        for (int i = 0; i < ja.length(); i++) {
            JSONObject joa = ja.getJSONObject(i);
            if (joa.get("type").equals("select")) {
                if (joa.get("name").equals(EVENT_TYPE)) {
                    JSONArray joOptions = new JSONArray(joa.get("options").toString());
                    JSONArray jaRes = new JSONArray();
                    for (int j = 0; j < joOptions.length(); j++) {
                        JSONObject option = joOptions.getJSONObject(j);
                        JSONObject res = new JSONObject();
                        String val = new JSONObject(option.get("label").toString()).get("en_US").toString();
                        if (!val.equalsIgnoreCase("none")) {
                            res.put("id", option.getString("value"));
                            res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
                            jaRes.put(res);
                        }
                    }
                    if (joa.get("name").equals(EVENT_TYPE))
                        renderRequest.setAttribute("EventType", helper.toList(jaRes));
                }
                if (joa.get("name").equals(ATTENDANCE)) {
                    JSONArray joOptions = new JSONArray(joa.get("options").toString());
                    JSONArray jaRes = new JSONArray();
                    for (int j = 0; j < joOptions.length(); j++) {
                        JSONObject option = joOptions.getJSONObject(j);
                        JSONObject res = new JSONObject();
                        String val = new JSONObject(option.get("label").toString()).get("en_US").toString();
                        if (!val.equalsIgnoreCase("none")) {
                            res.put("id", option.getString("value"));
                            res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
                            jaRes.put(res);
                        }
                    }
                    if (joa.get("name").equals(ATTENDANCE))
                        renderRequest.setAttribute("Attendance", helper.toList(jaRes));
                }
            }
        }
    }

    private List<JournalArticle> getFilteredArticles(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles, JSONArray filter, String fieldName) throws DocumentException {
        List<JournalArticle> returnedArticles = new ArrayList<>();

        for (JournalArticle article : fetchedArticles) {
            String content = article.getContentByLocale(themeDisplay.getLanguageId());

            Document document = SAXReaderUtil.read(content);
            String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='" + fieldName + "']").hasContent() ?
                    document.selectSingleNode("/root/dynamic-element[@name='" + fieldName + "']/dynamic-content").getText() : "";

            for (int i = 0; i < filter.length(); i++) {
                String val = filter.getString(i);
                if (fieldValue.equalsIgnoreCase(val)) {
                    returnedArticles.add(article);
                    break;
                }
            }
        }
        return returnedArticles;
    }

    private List<JournalArticle> getFilteredArticlesByStatus(List<JournalArticle> fetchedArticles,
                                                             ThemeDisplay themeDisplay) throws DocumentException, ParseException {
        List<JournalArticle> returnedArticles = new ArrayList<>();

        for (JournalArticle article : fetchedArticles) {
            String startDate = helper.getData(article, START_DATE, themeDisplay);
            String endDate = helper.getData(article, END_DATE, themeDisplay);
            Date startDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(startDate);
            Date endDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(endDate);

            CalendarEvent calendarEvent = getEventStatus(new CalendarEvent(), startDateDate, endDateDate);

            if (calendarEvent.isLive() || calendarEvent.isUpcoming()) {
                returnedArticles.add(article);
            }
        }
        return returnedArticles;
    }

    public List<JournalArticle> getPageLimit(List<JournalArticle> articles, int pageNum, int pageSize) {
        if (articles.isEmpty())
            return articles;

        int start = pageNum * pageSize, end = (pageNum + 1) * pageSize;
        if (start > articles.size()) {
            start = 0;
            end = pageSize;
        }
        if (end > articles.size()) {
            end = articles.size();
        }
        return articles.subList(start, end);
    }
}
