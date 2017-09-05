
export default class VisualisationAbsenceController {
    constructor(visualisationAbsenceService) {
        this.visualisationAbsenceService = visualisationAbsenceService;
    }

    $onInit() {
        this.absences = this.visualisationAbsenceService.findAll();
    }
}