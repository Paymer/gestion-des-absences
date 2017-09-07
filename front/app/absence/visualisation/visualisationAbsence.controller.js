
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

    // Partie suppression de l'absence

    supprimerAbsence(absenceId) {
        this.visualisationAbsenceService.supprimerAbsence(absenceId);
    }

    modification(idAbsence){
       this.visualisationAbsenceService.modification(idAbsence);
       console.log(idAbsence)
    }
}