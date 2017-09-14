
export default class VisualisationFerieController {
	constructor(visualisationFerieService, connexionService, $timeout) {
		this.visualisationFerieService = visualisationFerieService;
		this.connexionService = connexionService;
		this.timeout = $timeout
	}

	$onInit() {
		this.annees = [
		]
		this.feries = [
		]
		this.refreshView()
		this.order = "dateOriginal";
		this.triInverse = false;
	}

	refreshView() {
		this.visualisationFerieService.findAllAnnees()
			.then(result => {
				this.annees = result
				this.anneeCourante = "" + (new Date()).getFullYear()
				this.filterByAnnee()
			})
	}

	filterByAnnee() {
		this.visualisationFerieService.findByAnnee(this.anneeCourante)
			.then(result => this.feries = result)
	}

	updateOrderEtTri(order) {
		this.order = order;
		this.triInverse = !this.triInverse;
	}

	supprimerFerie(idFerie, date, type) {
		this.visualisationFerieService
			.supprimerFerie(idFerie, date, type).result.then((r) => {
			this.successMessage = r+" effectuée !"
			this.timeout(()=> {
				this.successMessage = null
				console.log(this.successMessage)
			}, 3000)
			this.refreshView()
		});
	}

	modification(idFerie, date, type, motif) {
		this.visualisationFerieService
			.modification(idFerie, date, type, motif);
	}

	isAdmin() {
		return this.connexionService.getGrade() == 'ADMINISTRATEUR';
	}
}