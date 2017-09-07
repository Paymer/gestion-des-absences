export default class MenuCtrl {
	constructor(logService, frontUrls, $location) {
		this.logService = logService
		this.$location = $location
		this.logo = {title: "Logo", url: "#"}
		this.frontUrls = frontUrls
		this.grade = (this.logService.getGrade() === 'MANAGER')

	}

	deconnecter() {
		this.logService.deconnecter();
		this.rediriger('xx')
	}

	isConnected() {
		return this.logService.isConnecte();
	}

	rediriger(a) {
		if (this.logService.isConnecte()) {
			switch (a) {
				case 'absence':
					this.$location.path(this.frontUrls.absence)
					break;
				case 'planningDemande':
					this.$location.path(this.frontUrls.planningDemande)
					break;
				case 'rapport':
					this.$location.path(this.frontUrls.rapport)
					break;
				case 'ferie':
					this.$location.path(this.frontUrls.ferie)
					break;
				case 'validation':
					this.$location.path(this.frontUrls.validationDemande)
					break;
				default:
					this.$location.path(this.frontUrls.accueil)
					break;
			}
		} else {
			this.$location.path(this.frontUrls.connexion)
		}
	}

}



MenuCtrl.$inject = [
	'connexionService',
	'frontUrls',
	'$location'
]