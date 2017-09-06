
export default class VisualisationAbsenceController {
    constructor(visualisationAbsenceService, connexionService) {
        this.visualisationAbsenceService = visualisationAbsenceService;
        this.connexionService = connexionService;
    }

    $onInit() {
        this.absences = this.visualisationAbsenceService.findAll();
        this.order = "dateDebut";
        this.triInverse = false;
    }

    updateOrderEtTri(order) {
        this.order = order;
        this.triInverse = !this.triInverse;
    }
}