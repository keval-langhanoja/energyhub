var calendar;
document.addEventListener('DOMContentLoaded', function () {
  document.getElementById("StartTime").setAttribute("min", getFormattedDate(new Date()));
  document.getElementById("EndTime").setAttribute("min", getFormattedDate(new Date()));
  //var languageEn = $('.langBtn').html() == 'English';
  //var languageAr = $('#hddLanguageAr').val();
  //var language = languageEn ? 'en' : 'ar-kw' ;
 
  var calendarEl = document.getElementById('calendar');
  calendar = new FullCalendar.Calendar(calendarEl, {
    //locale: language,
    initialView: 'dayGridMonth',
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
    //   buttonIcons: {
    //     prev: ' fas fa-caret-left',
    //     next: ' fas fa-caret-right'
    // },
    buttonText: {
      today: 'Today',
      month: 'Month',
      week: 'Week',
      day: 'Day',
      list: 'List'
    },
    height: 'auto',
    navLinks: true, // can click day/week names to navigate views
    selectable: true,
    selectMirror: true,
    nowIndicator: true,
    select: function (arg) {
      $("#addEventModal").modal("show");     
    },
    eventClick: function (event, jsEvent, view) {
      $("#addEventModal").modal("show");
      $('#EventTitle').val(event.event.title);
      $('#MeetingLink').val(event.event.extendedProps.MeetingLink);
      $('#StartTime').val(getFormattedDate(event.event.start));
      $('#EndTime').val(getFormattedDate(event.event.end));
      $('#Speakers').val(event.event.extendedProps.Speakers);
      $('#EventHost').val(event.event.extendedProps.EventHost);
    },
    //function(arg) {
    //  if (confirm('Are you sure you want to delete this event?')) {
    //    arg.event.remove()
    //  }
    // },
    events: [
		{
	      title: 'Event name 1',
	      start: '2022-03-16T14:30:00',
	      allDay: false,
	      end: '2022-03-16T16:30:00',
	      MeetingLink: 'Test.com',
	      Speakers: 'speakers',
	      EventHost: 'testhost'
	    },{
  	      title: 'Event name2',
  	      start: '2022-03-15T14:30:00',
  	      allDay: false,
  	      end: '2022-03-16T16:30:00',
  	      MeetingLink: 'Test.com',
  	      Speakers: 'speakers',
  	      EventHost: 'testhost'
  	    },{
	      title: 'Event name 3',
	      start: '2022-03-16T14:30:00',
	      allDay: false,
	      end: '2022-03-24T16:30:00',
	      MeetingLink: 'Test.com',
	      Speakers: 'speakers',
	      EventHost: 'testhost'
	    }
    ],
    eventMaxStack: 2,
    eventContent: function (arg) {
      let htmlTxt = document.createElement('b')
      htmlTxt.innerHTML = arg.event.title
      let arrayOfDomNodes = [htmlTxt]
      return { domNodes: arrayOfDomNodes }
    }
  });
  calendar.render();
});
$('#resetButton').on('click', function (e) {
  e.preventDefault();
  $("#addEventModal").modal('hide');
  clearModal();
});
$('#closeButton').on('click', function (e) {
  e.preventDefault();
  $("#addEventModal").modal('hide');
  clearModal();
});

$('#addEventButton').on('click', function (e) {
  e.preventDefault();
  addNewEvent();
});

function addNewEvent() {
  calendar.addEvent({
    title: $('#EventTitle').val(),
    start: $('#StartTime').val(),
    allDay: false,
    end: $('#EndTime').val(),
    MeetingLink: $('#MeetingLink').val(),
    Speakers: $('#Speakers').val(),
    EventHost: $('#EventHost').val()
  })
  calendar.unselect();
  clearModal();
  $("#addEventModal").modal('hide');

}
function clearModal() {
  $('#modal-body').find('input, textarea').val('');
  $('#modal-body span').remove('span');

}
function getFormattedDate(date) {
  var day = ('0' + date.getDate()).slice(-2);
  var month = ('0' + (date.getMonth() + 1)).slice(-2);
  var year = date.getFullYear().toString();
  var hour = date.getHours().toString();
  var minutes = ('0' + date.getMinutes()).slice(-2);
  //var seconds = ('0' + date.getSeconds()).slice(-2);
  return year + '-' + month + '-' + day + 'T' + hour + ':' + minutes;
}
function startDateChanged() {
  document.getElementById("EndTime").setAttribute("min", $('#StartTime').val());
}