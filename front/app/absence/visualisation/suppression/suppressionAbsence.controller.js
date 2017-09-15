
export default class SuppressionAbsenceController {
	constructor($uibModalInstance, suppressionAbsenceService, idAbsence,
		dateDebut, dateFin, type) {
		this.$uibModalInstance = $uibModalInstance;
		this.suppressionAbsenceService = suppressionAbsenceService;

		this.idAbsence = idAbsence;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.type = type;
		
		this.error = false
	}

	valider() {
		this.suppressionAbsenceService.supprimerAbsence(this.idAbsence)
			.then(() => this.$uibModalInstance.close("Suppression de l'absence"),
		() => this.error = true);
	}

	annuler() {
		this.$uibModalInstance.dismiss("Annulation de la suppression de l'absence");
	}
}