
export default class VisualisationAbsenceService {
    constructor(apiUrls, $resource) {
        this.apiUrls = apiUrls;
        this.$resource = $resource;

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
            if(a.statut == "INITIALE" && a.type != "Mission") {
                a.actions.push("modification");
            }
            if(a.type != "Mission") {
                a.actions.push("suppression");
            }
            if(a.type == "Mission") {
                a.actions.push("visualisation");
            }
            console.log(a.actions);
        })

    }
}