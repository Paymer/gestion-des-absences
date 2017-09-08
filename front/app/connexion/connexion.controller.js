export default class ConnexionController {
	constructor($http, $location, connexionService, apiUrls, jssha) {
		if (connexionService.isConnecte()) {
			$location.path('/')
		}
		this.$http = $http
		this.$location = $location
		this.connexion = connexionService
		this.apiUrls = apiUrls
		this.encrypt = jssha

		this.error = false;
		this.errorServer = false;
	}

	checkConnexion() {
		this.error = false;
		this.errorServer = false;

		if (this.email && this.pwd) {
			let password = new this.encrypt("SHA-1", "TEXT")
			password.update(this.pwd)
			password = password.getHash("HEX")
			const urlCheckConnexion =
				this.apiUrls.connexion + '/' + this.email + '/' + password
			this.$http.get(urlCheckConnexion, {})
				.then(result => {
					let user = result.data
					if (user.matricule) {
						this.connexion.connecter(user.matricule, user.nom,
							user.prenom, user.grade, user.email,
							user.departement, user.congesPayes,
							user.rtt)
						this.$location.path('/')
					} else {
						this.error = true
					}
				},
					() => {
					this.error = false
					this.errorServer = true
				})
		}
	}
}

ConnexionController.$inject = [
	'$http',
	'$location',
	'connexionService',
	'apiUrls',
	'jssha'
];