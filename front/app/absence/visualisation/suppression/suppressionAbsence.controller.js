
export default class SuppressionAbsenceController {
    constructor($uibModalInstance, suppressionAbsenceService, absenceId) {
        this.$uibModalInstance = $uibModalInstance;
        this.suppressionAbsenceService = suppressionAbsenceService;
        this.absenceId = absenceId;
    }

    valider() {
        console.log("A faire : supprimer l'absence d'id :", this.absenceId);
        this.suppressionAbsenceService.supprimerAbsence(this.absenceId);
        this.$uibModalInstance.close("Suppression de l'absence");
    }

    annuler() {
        this.$uibModalInstance.dismiss("Annulation de la suppression de l'absence");
    }
}