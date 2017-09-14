
export default class VisualisationAbsenceController {
    constructor(visualisationFerieService, connexionService) {
        this.visualisationFerieService = visualisationFerieService;
        this.connexionService = connexionService;
    }

    $onInit() {
		this.annees = []
		this.feries = []
        this.visualisationFerieService.findAllAnnees()
			.then(result => {
				this.annees = result
				this.anneeCourante = this.annees[0]
				this.filterByAnnee()
			})
        this.order = "dateOriginal";
        this.triInverse = false;
    }
	
	filterByAnnee(){
		this.visualisationFerieService.findByAnnee(this.anneeCourante)
			.then(result => this.feries = result)
	}


    updateOrderEtTri(order) {
        this.order = order;
        this.triInverse = !this.triInverse;
    }

    supprimerFerie(idFerie, date) {
        this.visualisationFerieService
			.supprimerFerie(idFerie, date);
    }

    modification(idFerie, date, type, motif) {
        this.visualisationFerieService
			.modification(idFerie, date, type, motif);
    }
	
	isAdmin(){
		return this.connexionService.getGrade()=='ADMINISTRATEUR';
	}
}