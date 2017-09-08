
export default class ValidationAbsenceController {
    constructor(validationAbsenceService, connexionService) {
        this.validation = validationAbsenceService;
        this.connexion = connexionService;
		
		this.errorMaj = false
		this.errorServeur = false;
    }

    $onInit() {
        this.validation.findAll().then(result => this.absences = result);
        this.order = "dateDebut";
        this.triInverse = false;
    }

    updateOrderEtTri(order) {
        this.order = order;
        this.triInverse = !this.triInverse;
    }

    setAbsence(idAbsence, statut) {
		this.errorMaj = false
		this.errorServeur = false;
		
        this.validation.setStatutDemande(idAbsence, statut)
		.then((response) => {
			if(response.success){
				this.validation.findAll().then(result => this.absences = result)
				this.errorMaj = false
				this.errorServeur = false;
			}else{
				this.errorMaj = true
			}
		},() => {
			this.errorServeur = true
		})
    }
}

ValidationAbsenceController.$inject = ['validationAbsenceService', 'connexionService']