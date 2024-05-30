<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.0/jquery.waypoints.min.js"></script>
<script src="/o/energy-hub-theme/js/jquery.counterup.min.js"></script>

<style>
	.activeUsers {
		line-height: 60px;
		color:#5a5a5a; 
		text-align: center;
		font-size: 30px;
	}
	
	.chartsBorder {
	    border: 1px solid #5a5a5a;
    	padding: 20px;
    	min-height: 300px !important;
    }
    .vertical-center {
	  margin: 0;
	  position: absolute;
	  top: 50%;
	  -ms-transform: translateY(-50%);
	  transform: translateY(-50%);
	}
</style>

<h1 style="font-size: 48px; font-weight: 400; margin: 2;">${reportName}</h1>

<div class="row mt-8">
	<div id="agesCount" class="col-6 chartsBorder"></div>
	<div class="col-6 activeUsers chartsBorder activeUsers chartsBorder d-flex align-items-center justify-content-center">
		<div class="">
	     	<h1><span class="counter" id="activeCount">${activeUsersCount}</span></h1>
	      	<h3>Current Active Users</h3>
	      	<img src="/o/energy-hub-theme/images/multi-user.png" style="width: 100px;height: 100px;"alt="">
		</div>
	</div>
</div>

<div class="row mt-8">
	<div id="roleCount" class="col-12 chartsBorder"></div>
</div>

<div class="row mt-8">
	<div id="genderCount" class="col-6 chartsBorder"></div>
	<div id="countryCount" class="col-6 chartsBorder"></div>
</div>

<script>
	$( document ).ready(function() {
	    $('#activeCount').counterUp({
		  delay: 10,
		  time: 1000
		});
	});
	
	google.charts.load('current', {'packages':['corechart', 'bar']});
    google.charts.setOnLoadCallback(drawRoleCount);
    google.charts.setOnLoadCallback(drawGenderCount);
    google.charts.setOnLoadCallback(drawCountryCount);
    google.charts.setOnLoadCallback(drawAgesCount);
    
	function drawRoleCount() {
		var roleCount = ${roleCount};
       	var data = new google.visualization.DataTable(); 

       	data.addColumn('string', 'EnergyHub User Roles');
        data.addColumn('number', 'Count');
       
        for(var i=0; i<roleCount.length;i++){
           	data.addRow([roleCount[i].Description, parseInt(roleCount[i].Total)]);
        } 
        var options = {
        	chart: {
          		title: 'User Counts by Role',
     		 	width: 900,
                height: 400,
	          	legend: { position: "none" },
        	}
   		};

        var roleCountChart = new google.charts.Bar(document.getElementById('roleCount'));
        roleCountChart.draw(data, options);
	}
	
	function drawGenderCount() {
		var genderCount = ${genderCount};
       	var data = new google.visualization.DataTable(); 

       	data.addColumn('string', 'Gender');
        data.addColumn('number', 'Count');
       
        for(var i=0; i<genderCount.length;i++){
           	data.addRow([genderCount[i].Gender, parseInt(genderCount[i].Total)]);
        } 
        var options = {
    		title: 'User Gender Count',
			colors: ['#FFC000', '#5B9BD5'],
			chartArea: { left: 20, width: '100%', height: '75%' },
			titleTextStyle: { color: '#5A5A5A', fontSize: 16 },
			pieSliceText: 'none',
			width: '100%',
			height: '100%'
   		};

        var genderCountChart = new google.visualization.PieChart(document.getElementById('genderCount'));
        genderCountChart.draw(data, options);
	}	
	
	function drawCountryCount() {
		var countryCount = ${countryCount};
       	var data = new google.visualization.DataTable(); 

       	data.addColumn('string', 'Country');
       	data.addColumn('number', 'Total');
       
        for(var i=0; i<countryCount.length;i++){
           	data.addRow([countryCount[i].Country, parseInt(countryCount[i].Total)]);
        } 
        var options = {
    		title: 'User Country Count',
			pieHole: 0.7,
			colors: ['#A5A5A5', '#5B9BD5'],
			chartArea: { left: 20, width: '100%', height: '75%' },
			titleTextStyle: { color: '#5A5A5A', fontSize: 16 },
			pieSliceText: 'none',
			width: '100%',
			height: '100%'
   		};

        var countryCountChart = new google.visualization.PieChart(document.getElementById('countryCount'));
        countryCountChart.draw(data, options);
	}	
	
	function drawAgesCount() {
		var agesCount = ${agesCount};
       	var data = new google.visualization.DataTable(); 

       	data.addColumn('string', 'AgeGroup');
       	data.addColumn('number', 'Total');
       
        for(var i=0; i<agesCount.length;i++){
           	data.addRow([agesCount[i].AgeGroup, parseInt(agesCount[i].Total)]);
        } 
        var options = {
    		title: 'User Ages Count',
			pieHole: 0.7,
			colors: ['#4472C4', '#ED7D31', '#A5A5A5', '#FFC000', '#5B9BD5', '#70AD47', '#264478', '#9E480E'],
			chartArea: { left: 20, width: '100%', height: '75%' },
			titleTextStyle: { color: '#5A5A5A', fontSize: 16 },
			pieSliceText: 'none',
			width: '100%',
			height: '100%'
   		};

        var agesCountChart = new google.visualization.PieChart(document.getElementById('agesCount'));
        agesCountChart.draw(data, options);
	}	
</script>