
export default class VisualisationAbsenceController {
    constructor(visualisationAbsenceService, connexionService) {
        this.visualisationAbsenceService = visualisationAbsenceService;
        this.connexionService = connexionService;
    }

    $onInit() {
        this.visualisationAbsenceService.findAll().then(result => this.absences = result);
        this.order = "dateDebut";
        this.triInverse = false;
        this.congesPayes = this.connexionService.getCongesPayes();
        this.rtt = this.connexionService.getRtt();
    }

    updateOrderEtTri(order) {
        this.order = order;
        this.triInverse = !this.triInverse;
    }

    // Partie suppression de l'absence
    supprimerAbsence(idAbsence, dateDebut, dateFin, type) {
        this.visualisationAbsenceService.supprimerAbsence(idAbsence, dateDebut, dateFin, type);
    }

    // Partie modification de l'absence
    modification(idAbsence){
       this.visualisationAbsenceService.modification(idAbsence);
       console.log(idAbsence)
    }
}