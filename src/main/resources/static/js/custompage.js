/**
 * 
 */

var visible = false;
var serialCode;
var row;

function goBack() {
    window.history.back();
}

$(document).ready(function(){
	
    $('.table tbody tr').click(function(){
        row = $(this);

    	serialCode = $(this).find("td").eq(0).html();
    	
        $(".btn.btn-primary.disabled").removeClass("disabled").addClass("active");
        $(".btn.btn-primary.active").val($(this).find("td").eq(0).html());
        $(this).addClass("selected").siblings().removeClass("selected");
        $(this).parent("");

        $("#selectEquipment").attr("href", "/equipments/"+serialCode);
        $("#editEquipment").attr("href", "/equipments/"+serialCode+"/update");
//        $("#deleteEquipment").attr("href", "/equipments/"+serialCode+"/delete");
        

        $("#selectMaintenance").attr("href", "/maintenances/"+serialCode);
        $("#selectMaintenanceWaiting").attr("href", "/maintenances/"+serialCode+"/waiting");
        $("#selectMaintenanceDoing").attr("href", "/maintenances/"+serialCode+"/doing");
        $("#selectMaintenanceDone").attr("href", "/maintenances/"+serialCode+"/done");
        $("#editMaintenance").attr("href", "/maintenances/"+serialCode+"/update");
        $("#giveMaintenance").attr("href", "/maintenances/"+serialCode+"/reports/create");

        $("#selectReport").attr("href", "/client/reports/"+serialCode);
        
        $("#changePassword").attr("href", "/accounts/"+serialCode+"/password");
        $("#editUser").attr("href", "/accounts/"+serialCode+"/update");
        $("#selectUser").attr("href", "/accounts/"+serialCode);

        
//        $(this).getElementById("editButton").href("/equipments/"+serialCode+"/update");
//        $(this).getElementById("deleteButton").href("/equipments/"+serialCode+"/delete");
        
	});
	

	
    $('#priority').mousemove(function(){            
        var valuePriority = $(this).val();
        $('#valuePriority').text("Aktualna wartość: "+valuePriority);
        $('#valuePriority').val(valuePriority);
	});
    
});

    $(window).on('load', function(){
        var valuePriority = $('#priority').val();
        $('#valuePriority').text("Aktualna wartość: "+valuePriority);
        $('#valuePriority').val(valuePriority);
	});

function progress(level){
	$.ajax({
	    type : "GET",
	    url : "/leader/maintenances/test",
	    data : {
	        "progress" : level
	    },
	    success: function(data){
	    	var result = "<thead><tr><th>Nr. zlecenia</th><th>Nazwa zlecenia</th><th>Progres</th></tr></thead>";
	    	result += "<tbody>";
	    	$.each(data, function(k,v){
	    		result += "<tr>";
	    		result += "<td>";
	    		result += v.serialCode;
	    		result += "</td>";
	    		result += "<td>";
	    		result += v.title;
	    		result += "</td>";
	    		result += "<td>";
	    		if(v.progress == "done"){
	    			result += "<span class='label label-success'>";
	    			result += v.progress;
	    			result += "</span>";
	    		}else if(v.progress == "doing"){
	    			result += "<span class='label label-info'>";
	    			result += v.progress;
	    			result += "</span>";
	    		}else if(v.progress == "waiting"){
	    			result += "<span class='label label-warning'>";
	    			result += v.progress;
	    			result += "</span>";
	    		}
	    		result += "</td>";
	    		result += "</tr>";
	    	});
	    	result += "</tbody>";
	    	$("#tableMaintenance").html(result);
	    	console.log("testGet successful!");
	    },
	    error: function(data, status, error) {
	        console.log("testGet with errors!");
	    }
	});
}

function deleteMaintenance(){
	$.ajax({
		type: "DELETE",
		url: "/maintenances/"+serialCode,
		success: function() {
			console.log("testDelete successful!");
			deleteRowInTable();

			var message = "<div class=\"alert alert-success alert-dismissable\">" +
                                          "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                                          "<strong>Sukces! </strong> Konserwacja została usunięta" +
                                          "</div>"
            $("#deleteResponse").html(message);
		},
		error: function(status, error) {
			console.log("testDelete with errors!");
			var message = "<div class=\"alert alert-danger alert-dismissable\">" +
                          "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                          "<strong>Błąd! </strong> Konserwacja nie została usunięta" +
                          "</div>"
            $("#deleteResponse").html(message);
		}
	});
}

function deleteEquipment(){
	$.ajax({
		type: "DELETE",
		url: "/equipments/"+serialCode,
		success: function() {
			console.log("testDelete successful!");
			deleteRowInTable();
			var message = "<div class=\"alert alert-success alert-dismissable\">" +
                          "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                          "<strong>Sukces! </strong> Sprzęt medyczny został usunięty" +
                          "</div>"
            $("#deleteResponse").html(message);
		},
		error: function(status, error) {
			console.log("testDelete with errors!");
			var message = "<div class=\"alert alert-danger alert-dismissable\">" +
                          "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                          "<strong>Błąd! </strong> Sprzęt medyczny nie został usunięty" +
                          "</div>"
            $("#deleteResponse").html(message);
		}
	});
}

function deleteUser(){
	$.ajax({
		type: "DELETE",
		url: "/users/"+serialCode,
		success: function() {
			console.log("testDelete successful!");
			deleteRowInTable();

			var message = "<div class=\"alert alert-success alert-dismissable\">" +
                          "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                          "<strong>Sukces! </strong> Użytkownik został usunięty" +
                          "</div>"
            $("#deleteResponse").html(message);
		},
		error: function(status, error) {
			console.log("testDelete with errors!");
			var message = "<div class=\"alert alert-danger alert-dismissable\">" +
                          "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                          "<strong>Błąd! </strong> Użytkownik nie został usunięty" +
                          "</div>"
            $("#deleteResponse").html(message);
		}
	});
}

function deleteRowInTable(){
    $(row).remove();
}

function readURL(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      $('#preview-image')
        .attr('src', e.target.result);
    };
    reader.readAsDataURL(input.files[0]);
  }
}

function changePassword(){

    var changePasswordObject = {};
    changePasswordObject["newPassword"] = $("#password").val();
    changePasswordObject["repeatedNewPassword"] = $("#repeatedPassword").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/account/password",
        data : JSON.stringify(changePasswordObject),
		success: function(result) {
		    $("#password").val("");
		    $("#repeatedPassword").val("");
			var message = "<div class=\"alert alert-success alert-dismissable\">" +
                              "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                              "<strong>Sukces! </strong>"+ result +
                              "</div>"
			$("#response-password").html(message);
		},
		error: function(error) {
			console.log("Error! ",error);
			var message = "<div class=\"alert alert-danger alert-dismissable\">" +
                          "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                          "<strong>Błąd! </strong>"+ result +
                          "</div>"
            $("#response-password").html(message);
		}
     });
}

function changePersonalInformation(){

    var changePersonalInformationObject = {};
    changePersonalInformationObject["firstName"] = $("#firstName").val();
    changePersonalInformationObject["lastName"] = $("#lastName").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/account/personalInformation",
        data : JSON.stringify(changePersonalInformationObject),
		success: function(result) {
		    $("#firstName").val("");
		    $("#lastName").val("");
			var message = "<div class=\"alert alert-success alert-dismissable\">" +
                              "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                              "<strong>Sukces! </strong>"+ result +
                              "</div>"
			$("#response-personal-information").html(message);
		},
		error: function(error) {
			console.log("Error! ",error);
			var message = "<div class=\"alert alert-danger alert-dismissable\">" +
                            "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>" +
                             "<strong>Błąd! </strong>"+ result +
                          "</div>"
            $("#response-personal-information").html(message);
		}
     });
}