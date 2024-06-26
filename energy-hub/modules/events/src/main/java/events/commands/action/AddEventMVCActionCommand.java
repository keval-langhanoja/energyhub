package events.commands.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.*;
import event.attendance.service.EventAttendanceLocalService;
import events.constants.EventsPortletKeys;
import events.entity.CalendarEvent;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(immediate = true, property = {"javax.portlet.name=" + EventsPortletKeys.EVENTS,
        "mvc.command.name=addEvent"}, service = MVCActionCommand.class)
public class AddEventMVCActionCommand implements MVCActionCommand {

    private static final Log _log = LogFactoryUtil.getLog(AddEventMVCActionCommand.class);

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

    @Override
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) {

        try {
            String key = ParamUtil.getString(actionRequest, "status");
            String eventId = ParamUtil.getString(actionRequest, "eventId");
            ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
            User currentUser = themeDisplay.getUser();
            if (key.equalsIgnoreCase("yes")) {
                _eventAttendanceLocalService.addEventAttendance(Long.parseLong(eventId), currentUser.getUserId(), currentUser.getFullName(), true, false, false);
            } else if (key.equalsIgnoreCase("maybe")) {
                _eventAttendanceLocalService.addEventAttendance(Long.parseLong(eventId), currentUser.getUserId(), currentUser.getFullName(), false, true, false);
            } else if (key.equalsIgnoreCase("no")) {
                _eventAttendanceLocalService.addEventAttendance(Long.parseLong(eventId), currentUser.getUserId(), currentUser.getFullName(), false, false, true);
            } else {
                String eventName = ParamUtil.getString(actionRequest, "EventName");
                String eventDescription = ParamUtil.getString(actionRequest, "EventDescription");
                String attendance = ParamUtil.getString(actionRequest, "Attendance");
                String meetingLink = ParamUtil.getString(actionRequest, "MeetingLink");
                String eventImage = ParamUtil.getString(actionRequest, "EventImage");
                String eventType = ParamUtil.getString(actionRequest, "EventType");
                String otherLinks = ParamUtil.getString(actionRequest, "OtherLinks");
                String startDate = ParamUtil.getString(actionRequest, "StartDate");
                String endDate = ParamUtil.getString(actionRequest, "EndDate");
                List<String> speakers = Collections.singletonList(ParamUtil.getString(actionRequest, "Speakers"));

                Date startSimpleFormatedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDate.replaceAll("T", " "));
                String startFormatedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").format(startSimpleFormatedDate);

                Date endSimpleFormatedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate.replaceAll("T", " "));
                String endFormatedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").format(endSimpleFormatedDate);

                CalendarEvent calendarEvent = new CalendarEvent();
                calendarEvent.setEventName(eventName);
                calendarEvent.setEventDescription(eventDescription);
                calendarEvent.setEventType(eventType);
                calendarEvent.setAttendance(attendance);
                calendarEvent.setMeetingLink(meetingLink);
                calendarEvent.setEventImageURL(eventImage);
                calendarEvent.setOtherLinks(otherLinks);
                calendarEvent.setStartDate(startFormatedDate);
                calendarEvent.setEndDate(endFormatedDate);
                calendarEvent.setSpeakers(speakers);

                addJournalArticle(actionRequest, themeDisplay, calendarEvent, actionRequest);
            }
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return false;
    }

    public void addJournalArticle(ActionRequest actionRequest, ThemeDisplay themeDisplay, CalendarEvent calendarEvent, ActionRequest request) {
        long userId = themeDisplay.getUserId();
        long groupId = themeDisplay.getLayout().getGroupId();
        ServiceContext serviceContext = new ServiceContext();

        com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructures().stream()
                .filter(st -> st.getName("en_US").equalsIgnoreCase("event")).findFirst().orElse(null);

        AssetCategory assetCategory = _assetCategoryLocalService.getCategories().stream()
                .filter(category -> category.getName().equalsIgnoreCase("Events")).findFirst().orElse(null);

        serviceContext.setAddGroupPermissions(true);
        serviceContext.setAddGuestPermissions(true);
        serviceContext.setScopeGroupId(groupId);
        if (Validator.isNotNull(assetCategory))
            serviceContext.setAssetCategoryIds(new long[]{assetCategory.getCategoryId()});
        Map<Locale, String> titleMap = new HashMap<>();
        Map<Locale, String> descriptionMap = new HashMap<>();

        try {
            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
            File file = uploadRequest.getFile("EventImage");

            long eventFolderId = GetterUtil.DEFAULT_LONG;
            long parentFolderId = GetterUtil.DEFAULT_LONG;
            List<JournalFolder> journalFolderList = JournalFolderLocalServiceUtil.getFolders(themeDisplay.getScopeGroupId(), GetterUtil.DEFAULT_LONG);
            for (JournalFolder journalFolder : journalFolderList) {
                if (journalFolder.getName().equalsIgnoreCase("Programs")) {
                    parentFolderId = journalFolder.getFolderId();
                }
            }
            List<JournalFolder> journalChildFolderList = JournalFolderLocalServiceUtil.getFolders(themeDisplay.getScopeGroupId(), parentFolderId);
            for (JournalFolder journalFolder : journalChildFolderList) {
                if (journalFolder.getName().equalsIgnoreCase("Events")) {
                    eventFolderId = journalFolder.getFolderId();
                }
            }

            JSONObject eventImage = fileUploadByDL(file, themeDisplay, request, parentFolderId, eventFolderId, "EventImageName");

            String xmlContent = "<?xml version=\"1.0\"?><root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">"
                    + "<dynamic-element field-reference=\"EventName\" index-type=\"keyword\" instance-id=\"mOKLscit\" name=\"" + EVENT_NAME + "\" type=\"text\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getEventName() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"Image\" index-type=\"text\" instance-id=\"pOgW0Xpr\" name=\"" + EVENT_IMAGE + "\" type=\"image\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + eventImage.toString() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"Attendance\" index-type=\"keyword\" instance-id=\"PWDHX8vb\" name=\"" + ATTENDANCE + "\" type=\"select\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getAttendance() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"MeetingLink\" index-type=\"keyword\" instance-id=\"IsBfvEX7\" name=\"" + MEETING_LINK + "\" type=\"text\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getMeetingLink() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"EventType\" index-type=\"keyword\" instance-id=\"SRjLaPfV\" name=\"" + EVENT_TYPE + "\" type=\"select\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getEventType() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"OtherLinks\" index-type=\"keyword\" instance-id=\"jb7LkrWx\" name=\"" + OTHER_LINKS + "\" type=\"text\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getOtherLinks() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"StartDate\" index-type=\"keyword\" instance-id=\"1tjS3CpR\" name=\"" + START_DATE + "\" type=\"text\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getStartDate() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"EndDate\" index-type=\"keyword\" instance-id=\"rfcEmmBl\" name=\"" + END_DATE + "\" type=\"text\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getEndDate() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"Speakers\" index-type=\"keyword\" instance-id=\"0ELlCC4u\" name=\"" + SPEAKERS + "\" type=\"text\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[" + calendarEvent.getSpeakers() + "]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "<dynamic-element field-reference=\"Description\" index-type=\"text\" instance-id=\"bxGLpx8V\" name=\"" + EVENT_DESCRIPTION + "\" type=\"rich_text\">"
                    + "<dynamic-content language-id=\"en_US\"><![CDATA[<p>" + calendarEvent.getEventDescription() + "</p>]]></dynamic-content>"
                    + "</dynamic-element>"
                    + "</root>";

            titleMap.put(Locale.US, calendarEvent.getEventName());
            descriptionMap.put(Locale.US, calendarEvent.getEventName());

            if (Validator.isNotNull(ddmStructure))
                JournalArticleLocalServiceUtil.addArticle("", userId, groupId, eventFolderId, titleMap, descriptionMap,
                        xmlContent, ddmStructure.getStructureId(), "", serviceContext);

        } catch (Exception e) {
            _log.error(e.getMessage());
        }
    }

    public JSONObject fileUploadByDL(File file, ThemeDisplay themeDisplay, ActionRequest request, long parentFolderId, long folderId, String fileTitleOrFileId) {
        long userId = themeDisplay.getUserId();
        long groupId = themeDisplay.getScopeGroupId();
        long repositoryId = themeDisplay.getScopeGroupId();
        JSONObject coverImageData = new JSONObject();
        try {

            ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
            serviceContext.setUserId(userId);
            serviceContext.setScopeGroupId(groupId);
            serviceContext.setWorkflowAction(0);

            long fileEntryId = CounterLocalServiceUtil.increment();
            String mimeType = MimeTypesUtil.getContentType(file);

            DLFileEntry dlFileEntry = _dlFileEntryLocalService.createDLFileEntry(fileEntryId);

            dlFileEntry.setUserId(userId);
            dlFileEntry.setGroupId(groupId);
            dlFileEntry.setFolderId(folderId);
            dlFileEntry.setRepositoryId(repositoryId);
            dlFileEntry.setFileName(file.getName());
            dlFileEntry.setMimeType(mimeType);
            dlFileEntry.setTitle(file.getName());
            dlFileEntry.setDescription(fileTitleOrFileId);
            dlFileEntry.setSize(file.length());
            dlFileEntry.setTreePath("/" + parentFolderId + "/" + folderId + "/");
            dlFileEntry.setName(String.valueOf(CounterLocalServiceUtil.increment()));
            dlFileEntry.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
            DLFileEntry addedDLFileEntry = _dlFileEntryLocalService.addDLFileEntry(dlFileEntry);

            _addFileEntryResources(addedDLFileEntry, serviceContext);

            long dlFileVersionId = CounterLocalServiceUtil.increment(DLFileVersion.class.getName());
            DLFileVersion dlFileVersion = DLFileVersionLocalServiceUtil.createDLFileVersion(dlFileVersionId);
            dlFileVersion.setUserId(userId);
            dlFileVersion.setFileEntryId(addedDLFileEntry.getFileEntryId());
            dlFileVersion.setFileName(addedDLFileEntry.getFileName());
            dlFileVersion.setExtension(addedDLFileEntry.getExtension());
            dlFileVersion.setMimeType(addedDLFileEntry.getMimeType());
            dlFileVersion.setTitle(addedDLFileEntry.getTitle());
            dlFileVersion.setGroupId(addedDLFileEntry.getGroupId());
            dlFileVersion.setVersion(addedDLFileEntry.getVersion());
            DLFileVersionLocalServiceUtil.addDLFileVersion(dlFileVersion);

            String FileURL =
                    PortalUtil.getPortalURL(request) + "/documents/" + dlFileEntry.getGroupId() + "/" +
                            dlFileEntry.getFolderId() + "/" + dlFileEntry.getFileName() + "/" + dlFileEntry.getUuid() + "?t=" +
                            System.currentTimeMillis();
            coverImageData.put("alt", "");
            coverImageData.put("name", dlFileEntry.getFileName());
            coverImageData.put("classNameId", dlFileEntry.getClassNameId());
            coverImageData.put("description", dlFileEntry.getDescription());
            coverImageData.put("fileEntryId", dlFileEntry.getFileEntryId());
            coverImageData.put("groupId", dlFileEntry.getGroupId());
            coverImageData.put("title", dlFileEntry.getTitle());
            coverImageData.put("type", dlFileEntry.getMimeType());
            coverImageData.put("url", FileURL);
            coverImageData.put("uuid", dlFileEntry.getUuid());

        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return coverImageData;
    }

    private void _addFileEntryResources(
            DLFileEntry dlFileEntry, ServiceContext serviceContext)
            throws PortalException {

        if (serviceContext.isAddGroupPermissions() ||
                serviceContext.isAddGuestPermissions()) {

            _resourceLocalService.addResources(
                    dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
                    dlFileEntry.getUserId(), DLFileEntry.class.getName(),
                    dlFileEntry.getFileEntryId(), false, serviceContext);
        } else {
            if (serviceContext.isDeriveDefaultPermissions()) {
                serviceContext.deriveDefaultPermissions(
                        dlFileEntry.getRepositoryId(),
                        DLFileEntryConstants.getClassName());
            }

            _resourceLocalService.addModelResources(
                    dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
                    dlFileEntry.getUserId(), DLFileEntry.class.getName(),
                    dlFileEntry.getFileEntryId(),
                    serviceContext.getModelPermissions());
        }
    }

    @Reference
    private DLFileEntryLocalService _dlFileEntryLocalService;

    @Reference
    private EventAttendanceLocalService _eventAttendanceLocalService;

    @Reference
    private ResourceLocalService _resourceLocalService;

    @Reference
    private AssetCategoryLocalService _assetCategoryLocalService;
}
