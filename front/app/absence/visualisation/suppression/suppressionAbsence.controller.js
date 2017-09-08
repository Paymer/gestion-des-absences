
export default class SuppressionAbsenceController {
    constructor($uibModalInstance, suppressionAbsenceService, idAbsence, dateDebut, dateFin, type) {
        this.$uibModalInstance = $uibModalInstance;
        this.suppressionAbsenceService = suppressionAbsenceService;

        this.idAbsence = idAbsence;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
    }

    valider() {
        this.suppressionAbsenceService.supprimerAbsence(this.idAbsence);
        this.$uibModalInstance.close("Suppression de l'absence");
    }

    annuler() {
        this.$uibModalInstance.dismiss("Annulation de la suppression de l'absence");
    }
}