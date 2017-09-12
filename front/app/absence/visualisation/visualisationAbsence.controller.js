
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

    // https://stackoverflow.com/questions/2086744/javascript-function-to-convert-date-yyyy-mm-dd-to-dd-mm-yy
    getDateBonFormat(date) {
        dArr = dateStr.split("-");  // ex input "2010-01-18"
        return dArr[2]+ "/" +dArr[1]+ "/" +dArr[0].substring(2); //ex out: "18/01/10"
    }
    

    // Partie suppression de l'absence
    supprimerAbsence(idAbsence, dateDebut, dateFin, type) {
        this.visualisationAbsenceService.supprimerAbsence(idAbsence, dateDebut, dateFin, type);
    }

    // Partie modification de l'absence
    modification(idAbsence){
       this.visualisationAbsenceService.modification(idAbsence);
    }
}