$(function () {
	$("#start-date").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#end-date").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#refusal-date").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#granted-from-date").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#granted-to-date").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#date_of_publication").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#publicationDate").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#inputStartDate").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#inputEndDate").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$("#webFormDateText").datepicker({ dateFormat: "dd-mm-yy", changeYear: true });
	$('a[rel="external"]').attr('target', '_blank');
});

function getURLParameter(param) {
	var pageUrl = window.location.search.substring(1);
	var urlVariables = pageUrl.split('&');
	for (var i=0; i<urlVariables.length; i++) {
		var parameterName = urlVariables[i].split('=');
		if (parameterName[0] == param) {
			return parameterName[1];
		}
	}				
}

function modalLoader() {
	$('#modalLoader').modal({
	    backdrop: true,
	    keyboard: true
	});
}

function wayBackLoader() {
	$('#importWayback').on('click', function(event) {
		modalLoader();
	});
}
