
import suppressionAbsenceTemplate from "./suppression/suppressionAbsence.html"
import SuppressionAbsenceController from "./suppression/suppressionAbsence.controller"

export default class VisualisationAbsenceService {
    constructor(apiUrls, $http, $uibModal, connexionService) {
        this.apiUrls = apiUrls;
        this.$http = $http;
        this.$uibModal = $uibModal;
        this.connexionService = connexionService;
    }

    findAll() {
        let matriculeEmploye = this.connexionService.getMatricule();
        this.absences = this.$http.get(this.apiUrls.absence + "/" + matriculeEmploye, {})
                  .then(result => {
                      return result.data;
                  })
                  .then(absences => {
                        this.ajoutActions(absences);
                        this.transformerDate(absences);
                        return absences;
        })

        return this.absences;
    }

    // Permet de définir quelles actions sont possibles lors de la visualisation des absences
    // Rajoute un tableau d'actions (bien que ce tableau contienne souvent un seul élément) à l'absence
    ajoutActions(absences) {
        absences.forEach(a => {
            a.actions = [];
            if(a.statut == "INITIALE" && a.type != "MISSION") {
                a.actions.push("modification");
            }
            if(a.type != "MISSION") {
                let now = Date.now();
                let dateDebut = new Date(a.dateDebut);
                if(dateDebut >= now) {
                    a.actions.push("suppression");
                }
            }
            if(a.type == "MISSION") {
                a.actions.push("visualisation");
            }
        })
    }

    // Transforme les dates du format yyyy-MM-DD au format DD/MM/yyyy
    transformerDate(absences) {
        absences.forEach(a => {
            // let dateDebut = new Date(a.dateDebut);
            let dArr = a.dateDebut.split("-");  // ex input "2010-01-18"
            a.dateDebut = dArr[2]+ "/" +dArr[1]+ "/" +dArr[0]; //ex out: "18/01/10"
            let dArr2 = a.dateFin.split("-");  // ex input "2010-01-18"
            a.dateFin = dArr2[2]+ "/" +dArr2[1]+ "/" +dArr2[0]; //ex out: "18/01/10"
        })
    }

    supprimerAbsence(idAbsence, dateDebut, dateFin, type) {
        this.$uibModal.open({
            template: suppressionAbsenceTemplate,
            controller: SuppressionAbsenceController,
            controllerAs: '$ctrl',
            resolve: {
                idAbsence: () => idAbsence,
                dateDebut: () => dateDebut,
                dateFin: () => dateFin,
                type: () => type
            }
        });
    }


     modification(id, dateDebut, dateFin, type, motif){
       this.absenceModifId = id;
       this.absenceModifType = type;
       this.absenceModifInit = dateDebut;
       this.absenceModifFin = dateFin;
       this.absenceModifMotif = motif;
       
    }
}