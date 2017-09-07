
export default class SuppressionAbsenceController {
    constructor($uibModalInstance) {
        this.$uibModalInstance = $uibModalInstance;
    }

    valider() {
        this.$uibModalInstance.close("Suppression de l'absence");
    }

    annuler() {
        this.$uibModalInstance.dismiss("Annulation de la suppression de l'absence");
    }
}