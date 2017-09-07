
export default class SuppressionAbsenceController {
    constructor($uibModalInstance, suppressionAbsenceService, idAbsence) {
        this.$uibModalInstance = $uibModalInstance;
        this.suppressionAbsenceService = suppressionAbsenceService;
        this.idAbsence = idAbsence;
    }

    valider() {
        this.suppressionAbsenceService.supprimerAbsence(this.idAbsence);
        this.$uibModalInstance.close("Suppression de l'absence");
    }

    annuler() {
        this.$uibModalInstance.dismiss("Annulation de la suppression de l'absence");
    }
}