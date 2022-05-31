$(function() {
    $("#getAllFilms-button").click(gafDataSelector);
    $("#getAllFilms-button").click(function(){$("#results-region-1").show(500);});
    $("#showHide-button1").click(function(){$("#results-region-1").hide(0);});
    $("#searchFilms-button").click(sfDataSelector);
    $("#searchFilms-button").click(function(){$("#results-region-2").show(500);});
    $("#showHide-button2").click(function(){$("#results-region-2").hide(0);});
    $("#insertFilm-button").click(insertFilm);
    $("#updateFilm-button").click(updateFilm);
    $("#deleteFilm-button").click(deleteFilm);
});

function insertText(text, resultRegion) {
  $(resultRegion).html(text);
}

function showHide() {
  var x = document.getElementById("results-region-1");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function gafDataSelector(){
	var dataType = $("#getAllFilms-form option:selected").val();
	console.log("DEBUG* In gafDataSelector(), dataType= " + dataType);
	getAllFilms(dataType);
}

function sfDataSelector(){
	var dataType = $("#searchFilms-form option:selected").val();
	console.log("DEBUG* In sfDataSelector(), dataType= " + dataType);
	searchFilms(dataType);
}

function searchFilms(dataType) {
  var address = "searchFilms";
  var params = $("#searchFilms-form").serialize();
  var data = "format=" + dataType + "&" + params;
  console.log("DEBUG* In searchFilms(), data= " +data);
  var resultRegion = "results-region-2";
  if("xml"==(dataType)){
    	ajaxPost(address, data, function(request) {showXmlFilmInfo(request, resultRegion);});
    }else if("json"==(dataType)){
    	ajaxPost(address, data, function(request) {showJsonFilmInfo(request, resultRegion);}); 
    }else{
    	ajaxPost(address, data, function(request) {showStringFilmInfo(request, resultRegion);});
    }
}

function getFilmTable(rows) {
  var headings = 
    [ "ID", "Title", "Year", "Director", "Stars", "Review" ];
  return(getTable(headings, rows));
}

function getAllFilms(dataType){
console.log("In getAllFilms(dataType)");
	var address = "getAllFilms";
	var data = "format=" + dataType;
	var resultRegion = "results-region-1";
	var request = getRequestObject();
	console.log("Request is: " + request);
	if("xml"==(dataType)){
    	ajaxPost(address, data, function(request) {showXmlFilmInfo(request, resultRegion);});
    }else if("json"==(dataType)){
    	ajaxPost(address, data, function(request) {showJsonFilmInfo(request, resultRegion);});
    }else{
    	ajaxPost(address, data, function(request) {showStringFilmInfo(request, resultRegion);});
    }
}

function showXmlFilmInfo(request, resultRegion) {
console.log("In xml info builder");
console.log("In xml info builder: request is:" + request);
var x = request.readyState;
console.log("In xml info builder: readyState is:" + x);
  if ((request.readyState == 4) &&
      (request.status == 200)) { 
    var xmlDocument = request.responseXML;
    console.log("In xml info builder: XML is:" + xmlDocument);
    var films = xmlDocument.getElementsByTagName("film");
    var rows = new Array(films.length);
    var subElementNames = ["id", "title", "year", "director", "stars", "review"];
    for(var i=0; i<films.length; i++) {
      rows[i] = 
        getElementValues(films[i], subElementNames);
    }
    
    var table = getFilmTable(rows);
    console.log("In xml info builder: table is:" + table);
    htmlInsert(resultRegion, table);
  }
}

function showJsonFilmInfo(request, resultRegion) {
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    var rawData = request.responseText;
    var films = eval("(" + rawData + ")");
    var rows = new Array();
    for(var i=0; i<films.length; i++) {
      var film = films[i];
      rows[i] = [film.id, film.title,
                 film.year, film.director, film.stars, film.review];
    }
    var table = getFilmTable(rows);
    htmlInsert(resultRegion, table);
  }
}

function showStringFilmInfo(request, resultRegion) {
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    var rawData = request.responseText;
    var films = rawData.split(/\n+/);
    var rows = new Array();
    for(var i=0; i<films.length; i++) {
      if (films[i].length > 1) {  // Ignore blank lines
      	var film = films[i].split("$");
      	 for(var i=0; i<film.length; i++) {
        rows.push(film[i].split("#"));
        }
      }
    }
    var table = getFilmTable(rows);
    htmlInsert(resultRegion, table);
  }
}

function insertFilm(){
	var address = "insertFilm";
  	var params = $("#insertFilm-form").serialize();
  	var resultRegion = "results-region-3";
  	ajaxPost(address, params, function(request) {showStringFilmInfo(request, resultRegion);});
}

function updateFilm(){
	console.log("In updateFilm");
	var address = "updateFilm";
	var params = $("#updateFilm-form").serialize();
	var resultRegion = "results-region-4";
	ajaxPost(address, params, function(request) {showStringFilmInfo(request, resultRegion);});
}

function deleteFilm(){
	console.log("In deleteFilm");
	var address = "deleteFilm";
	var params = $("#deleteFilm-form").serialize();
	var resultRegion = "results-region-5";
	ajaxPost(address, params, function(request) {showStringFilmInfo(request, resultRegion);});
}