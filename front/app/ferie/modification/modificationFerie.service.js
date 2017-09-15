export default class ModifFerieService {

	constructor(apiUrls, $http) {
		this.url = apiUrls.ferie;
		this.$http = $http;
	}

	confirmeEnvoiFerie(ferie) {
		return this.$http.patch(this.url, ferie)

	}

}