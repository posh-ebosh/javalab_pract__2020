let emailForm = document.querySelector('.emailForm');
emailForm.onsubmit = function(evt) {
	evt.preventDefault();
	alert("Заявка отправлена");

}

