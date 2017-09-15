
export default class SuppressionFerieController {
	constructor($uibModalInstance, connexionService, suppressionFerieService,
		idFerie, date, type) {
		this.$uibModalInstance = $uibModalInstance;
		this.suppressionFerieService = suppressionFerieService;
		this.connexionService = connexionService

		this.errorServer = false
		this.errorDelete = false
		this.errorMessage = false

		this.idFerie = idFerie;
		this.date = date;
		this.type = type;
	}

	valider() {
		this.errorServer = false
		this.errorDelete = false
		this.errorMessage = false

		let ferie = {
			matricule: this.connexionService.getMatricule(),
			idFerie: this.idFerie
		}
		let str = "Suppression "
		str += (this.type == 'RTT employeur') ? 'de la RTT employeur'
			: 'du jour ferié'
		this.suppressionFerieService.supprimerFerie(ferie)
			.then((result) => {
				if (result.data) {
					if (result.data.success) {
						this.$uibModalInstance.close(str);
					} else {
						this.errorMessage = result.data.message
					}
				} else {
					this.errorServer = true
				}
			}, () => {
				this.errorServer = true
			});
	}

	annuler() {
		let str = "Annulation de la suppression "
		str += (this.type == 'RTT employeur') ? 'de la RTT employeur'
			: 'du jour ferié'
		this.$uibModalInstance.dismiss(str);
	}
}