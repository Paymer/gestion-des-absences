
import suppressionAbsenceTemplate from "./suppression/suppressionAbsence.html"
import SuppressionAbsenceController from "./suppression/suppressionAbsence.controller"

export default class VisualisationAbsenceService {
    constructor(apiUrls, $resource, $uibModal) {
        this.apiUrls = apiUrls;
        this.$resource = $resource;
        this.$uibModal = $uibModal;

        this.absencesRes = this.$resource(this.apiUrls.absence);
        this.absences = this.absencesRes.query();
    }

    findAll() {

        this.absences.$promise.then(res => {
            this.ajoutActions();
        })
        return this.absences;
    }

    // Permet de définir quelles actions sont possibles lors de la visualisation des absences
    // Rajoute un tableau d'actions (bien que ce tableau contienne souvent un seul élément) à l'absence
    ajoutActions() {
        this.absences.forEach(a => {
            a.actions = [];
            if(a.statut == "INITIALE" && a.type != "MISSION") {
                a.actions.push("modification");
            }
            if(a.type != "MISSION") {
                a.actions.push("suppression");
            }
            if(a.type == "MISSION") {
                a.actions.push("visualisation");
            }
        })
    }

    supprimerAbsence(absenceId) {
        this.$uibModal.open({
            template: suppressionAbsenceTemplate,
            controller: SuppressionAbsenceController,
            controllerAs: '$ctrl',
            resolve: {
                absenceId: absenceId
            }
        });
    }


     modification(idAbsence){
       this.idModif = idAbsence;
    }
}