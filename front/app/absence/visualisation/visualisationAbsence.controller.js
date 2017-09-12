
export default class VisualisationAbsenceController {
    constructor(visualisationAbsenceService, connexionService) {
        this.visualisationAbsenceService = visualisationAbsenceService;
        this.connexionService = connexionService;
    }

    $onInit() {
        this.visualisationAbsenceService.findAll().then(result => this.absences = result);
        this.order = "dateDebut";
        this.triInverse = false;

        this.connexionService.getCongesPayesEtRttFromBase()
                              .then(result => {
                                    let res = result.data
                                    this.congesPayes = res.congesPayes;
                                    this.rtt = res.rtt;

                                    this.connexionService.setCongesPayes(this.congesPayes);
                                    this.connexionService.setRtt(this.rtt);
                               });
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
    modification(id, dateDebut, dateFin, type){
       this.visualisationAbsenceService.modification(id, dateDebut, dateFin, type);
    }
}