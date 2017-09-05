
export default class VisualisationAbsenceController {
    constructor(visualisationAbsenceService) {
        this.visualisationAbsenceService = visualisationAbsenceService;
    }

    $onInit() {
        this.absences = this.visualisationAbsenceService.findAll();
        this.order = "dateDebut"
    }

    updateOrder(order) {
        // TODO : rajouter le "reverse order"
        console.log(order);
        this.order = order;
    }
}