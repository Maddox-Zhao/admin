function toggleNext(el) {
	el.className = (el.className=="menu-closed")?" ":"menu-closed";
	var next = el.nextSibling;
	while(next.nodeType != 1) next = next.nextSibling;
	next.style.display = ((next.style.display == "none")?"block":"none");
}
function toggleNext2(el) {
	if(el.className == "menu-closed"){
		var next = el.nextSibling;
		while(next.nodeType != 1) next = next.nextSibling;
		next.style.display = ((next.style.display == "none")?"block":"none");
	}
}
function toggleNextByTagName(tname) {
	var ccn="clicker";
	var clickers = document.getElementsByTagName(tname);
	for (i=0; i<clickers.length; i++) {
		//clickers[i].className+=" "+ccn;
		clickers[i].onclick = function() { 
			toggleNext(this) 
		}
		toggleNext2(clickers[i]);
	}
}
window.onload = function(){ toggleNextByTagName('h4') }