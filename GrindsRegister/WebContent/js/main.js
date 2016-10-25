// The root URL for the RESTful services
var rootURLGrinds = "http://localhost:8080/GrindsRegister/rest/grinds/";
var rootURLSubjects = "http://localhost:8080/GrindsRegister/rest/subjects/";
var rootURLTutor = "http://localhost:8080/GrindsRegister/rest/tutor/";

var verifyLogin = 0;
var idleTime = 0;

var currentSubjectId;  //used to display grinds

var currentTutor; //might be used in find tutor by Id
var currentGrind;

var currentTutorId; //needed on login to find tutor by id...for displaying details for update

var grindsList; //used for displaying the grinds table
var subjectsList; //used for displaying the subjects table
var tutorList; //used in checkLoginDetails
var dataList;

var table; //used in displaySubjectsTable

var subName;

var userName;
var password;

var panelState = "up";
var grindSlideId = "grind";

//When the DOM is ready.
$(document).ready(function(){
	$( "section" ).tabs();
	getAllSubjects();
	getAllTutorsReg();
});


$(document).on('click','.showGrinds', function(){
	//store the requested subject in a variable
	currentSubjectId = $(this).attr('id');
	
	table.fnClearTable();
    
	$('#table_id').dataTable().fnDestroy();
	findGrindsBySub(currentSubjectId);
});

//Displaying Subjects and Grinds tables
//this is working fine!!!
var getAllSubjects = function(){
	console.log('getAllSubjects');
	$.ajax({
		type : 'GET',
		url : rootURLSubjects,
		dataType : "json", // data type of response
		success : displaySubjectsTable
	});
};



var findGrindsBySub= function(subId) {
	//alert("in findGrindsBySub");
	console.log('findGrindsBySub: ' + subId);
	$.ajax({
		type: 'GET',
		url: rootURLGrinds + subId,
		dataType: "json",
		success: displayGrindsTable
	});
};



var displaySubjectsTable = function(data){
	
	subjectsList = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$('#subjects_head').show();
	$('#grinds_head').hide();
	
	$('.tr_row').remove();
	$.each(subjectsList, function(index, subjects) {
		$('#table_id').append(
				'<tr><td>'+subjects.subject+'</td><td><input class="showGrinds" id="'+subjects.subjectId+'" type="button" value="Show Grinds" style="font-size: 15px;"/></td></tr>');
	});
	
	table = $('#table_id').dataTable();
};

var displayGrindsTable = function(data){
	$("#tabs-2").empty();
	
	grindsList = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	
	$.each(grindsList, function(index, grinds) {
		$('#tabs-2').append(
				'<div class="visibleContainer" id="'+grinds.grindId+'">'+
					'<div id="vcLeftCol">'+grinds.tut_name+'</div>'+
					'<div id ="vcRightCol">'+grinds.price+'</div><br style="clear:both;"/>'+
				'</div>'+
				'<div class="panel" id="grind'+grinds.grindId+'">'+
					'<div>'+grinds.email+'</div><br />'+
					'<div>'+grinds.phone+'</div><br />'+
					'<div>'+grinds.description+'</div>'+
				'</div><br />');
	});
	
};

$(document).on('click','.visibleContainer', function(){
	grindSlideId += $(this).attr('id');
	if(panelState == "up"){
		 $("#"+grindSlideId+"").slideDown("slow");
         panelState = "down";
         grindSlideId="grind";
	}
	else{
		 $("#"+grindSlideId+"").slideUp("slow");
         panelState = "up";
         grindSlideId="grind";
	}
});

//Tutor Registration Functions  !!!!working!!!!!
$(document).on('click','#register', function(){
	var inputCheck = 1;
	var errorMsg = '';
	userName = $('#username').val();
	
	tutorList = dataList == null ? [] : (dataList instanceof Array ? dataList : [ dataList ]);
	
	$.each(tutorList, function(index, tutors) {
		if(userName==tutors.username){
			errorMsg += 'The Username: "'+userName+'" is already in use!\n';
			inputCheck=0;
		}
	});
	$('#registration *').filter(':input').each(function() {
		if($(this).val() === ""){
			errorMsg += 'Empty Fields!! \n';
			inputCheck = 0;
			return false;
		}	    
	});
	if(($('#password').val()) != ($('#confirm_password').val())){
		errorMsg += 'Passwords do not match! \n';
		inputCheck = 0;
	}
	if($('#confirm_password').val().length < 8){
		errorMsg += 'Password must be at least 8 characters! \n';
		inputCheck = 0;
	}
	
	if(inputCheck == 1){
		addTutor();
		getAllTutorsReg();//Update the dataList of tutors when a new tutor is added
	}
	else{
		alert('Error(s): \n'+errorMsg);
	}
	return false;
});

var getAllTutorsReg = function(){
	$.ajax({
		type : 'GET',
		url : rootURLTutor,
		dataType : "json", // data type of response
		success : function(data){
			dataList = data;
		}
	});
};

var addTutor = function () {
	console.log('addTutor');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURLTutor,
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Tutor Registered successfully');
			$('#registration').trigger("reset");
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addTutor error: ' + textStatus);
		}
	});
};

//Helper function to serialize all the form fields into a JSON string
var formToJSON=function () {	
	var jsonString = JSON.stringify({
		"id": "4321", 
		"name": $('#reg_name').val(), 
		"email": $('#contact_email').val(),
		"phone": $('#phone').val(),
		"username": $('#username').val(),
		"password": $('#confirm_password').val()
		});
	return jsonString;
};


//Login Functions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
$(document).on('click','#loginButton', function(){
	userName = $('#login_username').val();
	password = $('#login_password').val();
	getAllTutors();
	return false;
});

var getAllTutors = function(){
	console.log('getAllTutors');
	$.ajax({
		type : 'GET',
		url : rootURLTutor,
		dataType : "json", // data type of response
		success : checkLoginDetails
	});
};

var checkLoginDetails = function(data){
	var loginCheck = 0;
	
	tutorList = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(tutorList, function(index, tutors) {
		if((userName == tutors.username) && (password == tutors.password)){
			loginCheck = 1;
			verifyLogin = 1;
			currentTutorId = tutors.id;
			$("#tabs-4").empty();
			tutorLoggedIn();
			return false;
		}
		else{
			return true;
		}
	});
	
	if (loginCheck == 0){
		alert('login details NOT found');
	}
};

//Logged in functions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

var tutorLoggedIn = function(){
	if(verifyLogin == 0){
		location.reload();
		alert('Please Login');
	}
	
	idleTimeCheck();
	
	$('#tabs-4').append(
			'<table id="tutorDetails" style="width:100%">'+
				'<tr><td><input type="submit" id="detailsButton" value="Tutor Details" class="tutorButton" style="font-size: 15px;"></td>'+
				'<td><input type="submit" id="grindsButton" value="Add Grinds" class="tutorButton" style="font-size: 15px;"></td>'+
				'<td><input type="submit" id="logout" value="Log out" class="tutorButton" style="font-size: 15px;"></td></tr>'+
			'</table><br />'+
			'<form id="tutorUpdateForm">'+
				'<fieldset id="details">'+
					'<label for="name"> &nbsp;Name: </label> <br />'+
					'<input type="text" name="name" id="update_name" size="30" value="" /> <br />'+
					'<label for="contact_email">&nbsp;E-mail address: </label> <br />'+
					'<input type="text" id="update_email" size="30" value="" /> <br />'+
					'<label for="phone"> &nbsp;Phone Number: </label> <br />'+
					'<input type="text" id="update_phone" size="30" value="" /> <br />'+
					'<label for="username"> &nbsp;Username: </label> <br />'+
					'<input type="text" id="update_username" size="30" maxLength="100" value="" /> <br />'+
					'<label for="password"> &nbsp;Password: </label> <br />'+
					'<input type="password" id="password_1" size="30" maxLength="100" value="" /> <br />'+
					'<label for="update_password"> &nbsp;Confirm Password: </label> <br />'+
					'<input type="password" id="update_password" size="30" maxLength="100" value="" /> <br />'+
				'</fieldset> <br />'+
			'<input type="submit" value="Update Tutor" id="updateDetails" class="tutorButton" style="font-size: 15px;"/>&nbsp;&nbsp;&nbsp;&nbsp;'+
			'<input type="submit" value="Delete Tutor" id="deleteDetails" class="deleteButton" style="font-size: 15px;"/>'+
			'</form>');
	
	findTutorById(currentTutorId);
	//grindList=0;
};

$(document).on('click','#detailsButton', function(){
	$("#tabs-4").empty();
	tutorLoggedIn();
	return false;
});


var findTutorById= function(id) {
	$.ajax({
		type: 'GET',
		url: rootURLTutor + id,
		dataType: "json",
		success: function(data){
			currentTutor = data;
			renderTutorDetails(currentTutor);
		}
	});
};

var renderTutorDetails=function(tutor) {
	$('#update_name').val(tutor.name);
	$('#update_email').val(tutor.email);
	$('#update_phone').val(tutor.phone);
	$('#update_username').val(tutor.username);
	$('#password_1').val(tutor.password);
	$('#update_password').val(tutor.password);
};

//functions for Tutor Update!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

$(document).on('click','#updateDetails', function(){
	var inputCheck = 1;
	var errorMsg = '';
	userName = $('#update_username').val();
	
	tutorList = dataList == null ? [] : (dataList instanceof Array ? dataList : [ dataList ]);
	
	$.each(tutorList, function(index, tutors) {
		if((userName==tutors.username) && ((currentTutorId) != (tutors.id))){
			errorMsg += 'The Username: "'+userName+'" is already in use!\n';
			inputCheck=0;
		}
	});
	$('#tutorUpdateForm *').filter(':input').each(function() {
		if($(this).val() === ""){
			errorMsg += 'Empty Fields!! \n';
			inputCheck = 0;
			return false;
		}	    
	});
	if(($('#password_1').val()) != ($('#update_password').val())){
		errorMsg += 'Passwords do not match! \n';
		inputCheck = 0;
	}
	if($('#update_password').val().length < 8){
		errorMsg += 'Password must be at least 8 characters! \n';
		inputCheck = 0;
	}
	
	if(inputCheck == 1){
		updateTutor(currentTutorId); 
		findTutorById(currentTutorId);//currentTutor var needs to be updated or wrong details used when creating grinds
		getAllTutorsReg();//need to update the dataList so tutor details are accurrate
	}
	else{
		alert('Error(s): \n'+errorMsg);
	}
	
	
	
	return false;
});

var updateTutor= function (id) {
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURLTutor + id,
		data: formToJsonUpdate(),
		success: function(data, textStatus, jqXHR){
			alert("Tutor Details Updated Successfully");
			userName = $('#update_username').val(); //need to be updated so that UN and Pword match when getAllTutors is called 
			password = $('#update_password').val();
			$("#tabs-4").empty();
			getAllTutors();
		}
	});
};

//Helper function to serialize all the form fields into a JSON string
var formToJsonUpdate=function () {	
	var jsonString = JSON.stringify({
		"id": currentTutorId, 
		"name": $('#update_name').val(), 
		"email": $('#update_email').val(),
		"phone": $('#update_phone').val(),
		"username": $('#update_username').val(),
		"password": $('#update_password').val()
		});
	return jsonString;
};

//DELETE tutor details functions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
$(document).on('click','#deleteDetails', function(){
	checkGrindsByTut(currentTutorId);
	return false;
});

var checkGrindsByTut= function(tutId) {
	$.ajax({
		type: 'GET',
		url: rootURLGrinds + 'select/'+ tutId,
		dataType: "json",
		success: function(data){
			grindsList = data;
			var r = confirm('Are you sure you want to delete this account?');
			if(r == true){
				if(grindsList.length == 0){
					deleteTutor(currentTutorId);
				}
				else{
					deleteTutorGrinds(currentTutorId);
					deleteTutor(currentTutorId);
				}
				 
			}
			return false;
		}
	});
};

var deleteTutorGrinds=function(id) {
	$.ajax({
		type: 'DELETE',
		url: rootURLGrinds + 'allGrinds/' + id,
		success: function(data, textStatus, jqXHR){
			
		}
	});
};

var deleteTutor=function(id) {
	$.ajax({
		type: 'DELETE',
		url: rootURLTutor + id,
		success: function(data, textStatus, jqXHR){
			alert('Tutor deleted successfully');
			location.reload();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteTutor error');
		}
	});
};

//GRINDS functions, Logged in!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

$(document).on('click','#grindsButton', function(){
	$("#tabs-4").empty();
	grindsSection(subjectsList);
	return false;
});

var grindsSection = function(data){
	
	if(verifyLogin == 0){
		location.reload();
		alert('Please Login');
	}
	
	idleTimeCheck();
	
	subjectsList = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$('#tabs-4').append(
			'<table id="tutorDetails" style="width:100%">'+
				'<tr><td><input type="submit" id="detailsButton" value="Tutor Details" class="tutorButton" style="font-size: 15px;"></td>'+
				'<td><input type="submit" id="grindsButton" value="Add Grinds" class="tutorButton" style="font-size: 15px;"></td>'+
				'<td><input type="submit" id="logout" value="Log out" class="tutorButton" style="font-size: 15px;"></td></tr>'+
			'</table><br /><br />'+
			'<div id="leftCol" class="verticalLine" style="width:50%">'+
			'<p>Grind Details:</p>'+
			'<form ="grindsForm">'+
				'<fieldset id="details">'+
				'<label for="selectSubject"> &nbsp;Select Subject: </label> <br />'+
				'<select id="subjectSelect">'+
				
				'</select><br />'+
				'<label for="price">&nbsp;Price: </label> <br />'+
				'<input type="text" id="price" size="30" value="" required="required"/> <br />'+
				'<label for="description">&nbsp;Description: </label> <br />'+
				'<textarea id="description" rows="6" col="40" required="required"></textarea><br />'+
				'</fieldset> <br />'+
				'<input type="submit" value="Create Grind" id="createGrinds" class="tutorButton" style="font-size: 15px;"/>'+
				'<input type="submit" value="Update Grind" id="updateGrinds" class="tutorButton" style="font-size: 15px;"/>&nbsp;&nbsp;&nbsp;&nbsp;'+
				'<input type="submit" value="Delete Grind" id="deleteGrinds" class="deleteButton" style="font-size: 15px;"/>'+
			'</form>'+
			'</div>'+
			//Code for the right side 
			'<div id="rightCol" style="width:45%">'+
			'<p>Select a Grind to Update:</p>'+
			'<form>'+
				'<fieldset id="details">'+
				'<select id="GrindUpdateList">'+
			
				'</select>'+
				'</fieldset><br />'+
				'<input type="submit" value="Select Grind" id="selectGrind" class="tutorButton" style="font-size: 15px;"/>'+
			'</form>'+
			'</div>'
			);
	
	$('#createGrinds').show();
	$('#updateGrinds').hide();
	$('#deleteGrinds').hide();
	
	//SELECT A SUBJECT for CREATE GRIND!!!!!!!!!!!!
	$.each(subjectsList, function(index, subjects) {
		$('#subjectSelect').append(
			'<option id="'+subjects.subjectId+'">'+subjects.subject+'</option>'
		);
	});
	
	//SELECT A GRIND FOR UPDATE!!!!!!!
	findGrindsByTut(currentTutorId);
	
	
	
};

//GET the Subject Name
var getSubName = function(id){
	$.each(subjectsList, function(index, subjects) {
		if (subjects.subjectId == id){
			subName = subjects.subject;
		}
	});
};

var findGrindsByTut= function(tutId) {
	$.ajax({
		type: 'GET',
		url: rootURLGrinds + 'select/'+ tutId,
		dataType: "json",
		success: function(data){
			grindsList = data;
			$.each(grindsList, function(index, grinds) {
				getSubName(grinds.subjectId);
				$('#GrindUpdateList').append(
					'<option id="'+grinds.grindId+'">'+subName+' : '+grinds.price+'</option>'
				);
			});
		}
	});
};
//CREATE grinds functions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

$(document).on('click','#createGrinds', function(){
	
	currentSubjectId = $('#subjectSelect option:selected').attr('id');
	var checkVal = document.getElementById("price");
	var checkVal2 = document.getElementById("description");
	
	if ((checkVal.checkValidity() == false) || (checkVal2.checkValidity() == false)){
		alert('All Fields must be filled!');
	}
	else{
		addGrind();
	}
	
	return false;
});

var addGrind = function () {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURLGrinds,
//		dataType: "json",
		data: formToJsonGrinds(),
		success: function(data, textStatus, jqXHR){
			alert('Grind Created successfully');
			$("#tabs-4").empty();
			grindsSection(subjectsList);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addGrind error: ' + textStatus);
		}
	});
};

//Helper function to serialize all the form fields into a JSON string
var formToJsonGrinds=function () {	
	var jsonString = JSON.stringify({
		"grindId": "4321", 
		"tutorId": currentTutorId,
		"subjectId": currentSubjectId,
		"tut_name": currentTutor.tut_name,
		"email": currentTutor.email,
		"phone": currentTutor.phone,
		"price": $('#price').val(),
		"description": $('#description').val()
		});
	return jsonString;
};

//DISPLAY SELECTED GRIND FOR UPDATE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

$(document).on('click','#selectGrind', function(){
	findGrindsById($('#GrindUpdateList option:selected').attr('id'));
	$('#createGrinds').hide();
	$('#updateGrinds').show();
	$('#deleteGrinds').show();
	return false;
});

var findGrindsById= function(gId) {
	$.ajax({
		type: 'GET',
		url: rootURLGrinds + 'getGrind/'+ gId,
		dataType: "json",
		success: function(data){
			currentGrind = data;
			renderGrindDetails(currentGrind);
		}
	});
};

var renderGrindDetails=function(grind) {
	getSubName(grind.subjectId);
	$('#subjectSelect').val(subName); //how to preselect a select option
	$('#price').val(grind.price);
	$('#description').val(grind.description);
};

//GRIND UPDATE functions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

$(document).on('click','#updateGrinds', function(){
	currentSubjectId = $('#subjectSelect option:selected').attr('id');
	var checkVal = document.getElementById("price");
	var checkVal2 = document.getElementById("description");
	
	if ((checkVal.checkValidity() == false) || (checkVal2.checkValidity() == false)){
		alert('All Fields must be filled!');
	}
	else{
		updateGrind(currentGrind.grindId);
	}
	return false;
});

var updateGrind= function (id) {
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURLGrinds + id,
//		dataType: "json",
		data: formToJsonGrindsUpdate(),
		success: function(data, textStatus, jqXHR){
			alert('Grind updated successfully');
			$("#tabs-4").empty();
			grindsSection(subjectsList);
		}
	});
};

var formToJsonGrindsUpdate=function () {	
	var jsonString = JSON.stringify({
		"grindId": currentGrind.grindId, 
		"tutorId": currentTutorId,
		"subjectId": currentSubjectId,
		"tut_name": currentTutor.tut_name,
		"email": currentTutor.email,
		"phone": currentTutor.phone,
		"price": $('#price').val(),
		"description": $('#description').val()
		});
	return jsonString;
};

//DELETE GRIND!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

$(document).on('click','#deleteGrinds', function(){
	
	var r = confirm('Are you sure you want to delete this grind?');
	if(r == true){
		deleteGrind(currentGrind.grindId);
		$("#tabs-4").empty();
		grindsSection(subjectsList);
	}
	return false;
});

var deleteGrind=function(id) {
	$.ajax({
		type: 'DELETE',
		url: rootURLGrinds + id,
		success: function(data, textStatus, jqXHR){
			alert('Grind deleted successfully');
		}
	});
};

//LOG OUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
$(document).on('click','#logout', function(){
	verifyLogin = 0;
	location.reload();
});

var idleTimeCheck = function(){
	//Increment the idle time counter every minute.
	setInterval(timerIncrement, 60000);
	
	//Zero the idle timer on mouse movement.
    $(this).mousemove(function (e) {
        idleTime = 0;
    });
    $(this).keypress(function (e) {
        idleTime = 0;
    });
};

var timerIncrement = function(){
	idleTime = idleTime +1;
	if (idleTime > 15){
		location.reload();
		verifyLogin = 0;
		alert('You were logged out due to inactivity!');
	}
};

//FORGOT PASSWORD functions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

$(document).on('click','#forgotPassword', function(){
	$("#tabs-4").empty();
	
	$('#tabs-4').append(
			'<div style="width:100%" style="height:100%" id="emailContainer">'+
			'<p id="emailInfo">Enter your email address:</p>'+
			'<form id="login">'+
				'<fieldset id="inputs">'+
					'<input type="text" id="sendEmail" size="30" value="" required="required"/> <br />'+
				'</fieldset>'+
				'<fieldset id="actions" style="text-align:center;">'+
					'<input type="submit" value="Send Email" id="sendEmailButton"/>'+
				'</fieldset>'+
				'<p id="emailInfo">Enter your email address above and an email will be sent with your Username and a new password.</p>'+
			'</form>'+
			'</div>'
	);
	return false;
});

$(document).on('click','#sendEmailButton', function(){
	getAllTutorsReg();
	tutorList = dataList == null ? [] : (dataList instanceof Array ? dataList : [ dataList ]);
	var emailEntered = $('#sendEmail').val();
	var emailCheck = 0;
	
	$.each(tutorList, function(index, tutors) {
		if(emailEntered == tutors.email){
			currentTutorId = tutors.id;
			changeTutorPword(currentTutorId);
			tempPwordTutById(currentTutorId);
			emailCheck=1;
			return false;
		}
	});
	
	if (emailCheck == 0){
		alert('ERROR: Email Address not found!!');
	}
	
	return false;
});

var changeTutorPword= function (id) {
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURLTutor + 'tempPword/' + id,
		dataType: "json",
		data: formToJsonPword(),
		success: function(data, textStatus, jqXHR){
			
		}
	});
};

//Helper function to serialize all the form fields into a JSON string
var formToJsonPword=function () {	
	var jsonString = JSON.stringify({
		"id": currentTutorId, 
		"name": "TempTest", 
		"email": "TempTest",
		"phone": "TempTest",
		"username": "TempTest",
		"password": "TempTest"
		});
	return jsonString;
};

var tempPwordTutById= function(id) {
	$.ajax({
		type: 'GET',
		url: rootURLTutor+ 'tempPword/' + id,
		dataType: "json",
		success: function(data){
			currentTutor = data;
			sendMail();
		}
	});
};

var sendMail = function() {
	$.ajax({
		type: 'POST',
		url: 'https://mandrillapp.com/api/1.0/messages/send.json',
		data: {
			'key': 'CMd-yD8BNP9LUT0ObW_yIg',
			'message': {
				'from_email': 'jonathan.larrigy@gmail.com',
				'to': [
				    {
				      'email': ''+currentTutor.email+'',
				      'name': ''+currentTutor.name+'',
				      'type': 'to'
				    }
				   ],
				  'autotext': 'true',
				  'subject': 'Grind Register: Temporary Password',
				  'text': 'Hi '+currentTutor.name+',\n\n'+
				  		  'Please find below your current details for logging into the Grinds Register.\n'+
				  		  '\nUsername: '+currentTutor.username+
				  		  '\nPassword: '+currentTutor.password+
				  		  '\n\nKind Regards,'+
				  		  '\nGrinds Register Admin'
			}
		}
	}).done(function(response){
		alert('An email has been sent with your username and password!');
		location.reload();
	});

};




