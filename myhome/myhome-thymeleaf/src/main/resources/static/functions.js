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

function populateUnits(price, buyValue) {
	var elemUnits = document.getElementById('purchasedUnits');
	if (!price) {
		elemUnits.value = "";
	} else {
		elemUnits.value = (buyValue / price).toFixed(5);
	}
}


function parseSlovakDate(dateString) {
	if (!dateString) {
		return null;
	}
	
	var parts = dateString
		.split(".")
		.map((i) => Number.parseInt(i))
		.reverse();
		
	if (parts.length != 3) {
		return null;
	}
	
	if (parts.filter( p => isNaN(p) || p <= 0).lenght > 0) {
		return null;
	}
	
	
	// because monts are from 0
	var rv = new Date(parts[0], parts[1] - 1, parts[2]);
	
	// TODO detect day overflows, leap years
	if (rv.getMonth() != (parts[1] - 1) && rv.getDay() != parts[2]) {
		return null;
	}
	
	return rv;
}

var myCache = {};

function calculateUnits() {
	
	var buyPriceInput = document.getElementById('buyPrice');
	var buyPrice = Number(buyPriceInput.value);
	
	if (buyPriceInput.value && !isNaN(buyPrice)) {
		console.log("buyPrice ok");
	} else {
		console.log("buyPrice BAD: " + buyPriceInput.value);
		return;
	}
	
	var purchaseDateInput = document.getElementById('dateOfPurchase');
	
	var parsedDate = parseSlovakDate(purchaseDateInput.value);
	
	if (!parsedDate) {
		console.log("purchase date BAD: " + purchaseDateInput.value);
		return null;
	}
	
	
	var cacheKey = parsedDate.getTime();
	var fundPrice = myCache[parsedDate.getTime()];
	if (fundPrice) {
		console.log("Cache hit: " + fundPrice);
		populateUnits(fundPrice, buyPrice);
	} else {
		var fondIdVal = document.getElementById('fondId').value;
		var requestUrl = "http://localhost:8090/api/fondPrice?";
		requestUrl = requestUrl + "fondId=" + fondIdVal + "&date=" + purchaseDateInput.value;
	
		console.log("calling: "  + requestUrl);
		fetch(requestUrl)
			.catch(() => console.log("Fond price service response error!"))
			.then(response => response.json())
			.then(data => { 
				fundPrice = data.price;
				console.log('retrived price: ' + fundPrice);
				myCache[parsedDate.getTime()] = fundPrice;
				populateUnits(fundPrice, buyPrice);
			});
	}
}
