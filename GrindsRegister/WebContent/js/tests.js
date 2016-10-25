var rootURLGrinds = "http://localhost:8080/GrindsRegister/rest/grinds/";
var rootURLSubjects = "http://localhost:8080/GrindsRegister/rest/subjects/";
var rootURLTutor = "http://localhost:8080/GrindsRegister/rest/tutor/";

var subjectsList;

QUnit.test( "a basic test example", function( assert ) {
    var value = "hello";
    assert.equal( value, "hello", "We expect value to be hello" );
 });



	$.ajax({
		type : 'GET',
		url : rootURLSubjects,
		dataType : "json", // data type of response
		success : function(data){
			subjectsList = data == null ? [] : (data instanceof Array ? data : [ data ]);
		}
	});


//Get all subjects call
QUnit.test("Get All Subjects Test", function(assert){
	var listLength = subjectsList.length();
	assert.deepEqual(6, listLength);
});