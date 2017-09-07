export default class ValidationAbsenceService {
    constructor(apiUrls, $http, connexionService) {
        this.apiUrls = apiUrls
        this.$http = $http
		this.connexion = connexionService
    }

    findAll() {
		const urlGetAbsences = this.apiUrls.validationAbsence+'/'+this.connexion.getMatricule()
		return this.$http.get(urlGetAbsences,{}).then(result => result.data)
    }
	
	setStatutDemande(idAbsence, statut){
		const urlSetStatutDemande = this.apiUrls.validationAbsence+'/'+this.connexion.getMatricule()+'/'+idAbsence+'/'+statut
		return this.$http.patch(urlSetStatutDemande, {}).then(result => result.data)
	}
}

ValidationAbsenceService.$inject = ['apiUrls', '$http', 'connexionService']