export default class PlanningAbsenceService {

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
                return absences;
            })

        return this.absences;
    }

    // Permet de définir quelles actions sont possibles lors de la visualisation des absences
    // Rajoute un tableau d'actions (bien que ce tableau contienne souvent un seul élément) à l'absence
    ajoutActions(absences) {
        absences.forEach(a => {
            a.actions = [];
            if (a.statut == "INITIALE" && a.type != "MISSION") {
                a.actions.push("modification");
            }
            if (a.type != "MISSION") {
                a.actions.push("suppression");
            }
            if (a.type == "MISSION") {
                a.actions.push("visualisation");
            }
        })
    }
}