// for summary dialog, clears prices in tables, so newest prices will be loaded
function clearPrices() {
	const table = document.getElementsByTagName('table')[0];
	
	if (table) {
		for (const i of table.getElementsByTagName('input')) {
			console.log(i.getAttribute('value'));
			i.setAttribute('value', '');
		}
	}
}



