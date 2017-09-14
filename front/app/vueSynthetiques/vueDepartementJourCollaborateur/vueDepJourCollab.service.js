
export default class VueDepJourCollabService {
    constructor(apiUrls, $http, connexionService, visualisationAbsenceService) {
        this.apiUrls = apiUrls;
        this.$http = $http;
        this.connexionService = connexionService;
        this.visualisationAbsenceService = visualisationAbsenceService;
    }

    nombreJoursDuMois(moisCourant, anneeCourante) {
        return new Date(anneeCourante, moisCourant, 0).getDate();
    }

    getMoisEnLettres() {
        return ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"];
    }

    getMoisEnNombre() {
        return [0,1,2,3,4,5,6,7,8,9,10,11];
    }

    getDepartements() {
        return this.$http.get(this.apiUrls.departement, {})
             .then(result => result.data)
    }

    getSubalternes() {
        let matriculeEmploye = this.connexionService.getMatricule();
        return this.$http.get(this.apiUrls.collaborateur + "/" + matriculeEmploye, {})
             .then(result => result.data);
    }

    getMoisEnChiffre(moisEnLettres) {
        return this.getMoisEnLettres().indexOf(moisEnLettres);
    }

    getAbsencesParMatricule(matricule) {
        return this.$http.get(this.apiUrls.absence + "/" + matricule, {})
                  .then(result => {
                      return result.data;
                  }).then(absences => {
                        return this.$http.get(this.apiUrls.mission + "/" + matricule, {})
                            .then(result => {
                                this.visualisationAbsenceService.ajoutMissions(absences, result.data)
                                // this.visualisationAbsenceService.transformerDate(absences);
                                return absences;
                            });
                    });
    };

    ajoutTableauAbsences(s, nbJours, anneeCourante, moisCourantChiffre) {

        s.tableauAbsences = new Array(nbJours);
        
        let i;
        for(i=0 ; i<s.tableauAbsences.length ; i++) {
            let jour = new Date(anneeCourante, moisCourantChiffre, i+1);
            s.absences.forEach(a => {
                let dayOfWeek = jour.getDay();
                let isWeekend = (dayOfWeek == 6) || (dayOfWeek == 0); 
                if(moment(jour).isBetween(a.dateDebut, a.dateFin, "day", "[]") && !isWeekend) {
                    switch(a.type) {
                        case "CONGES_PAYES": s.tableauAbsences[i] = 'C'; break;
                        case "RTT": s.tableauAbsences[i] = 'R'; break;
                        case "CONGES_SANS_SOLDE": s.tableauAbsences[i] = 'S'; break;
                        case "MISSION": s.tableauAbsences[i] = 'M'; break;
                        case "RTT_EMPLOYEUR": s.tableauAbsences[i] = 'R'; break;
                        case "JOUR_FERIE": s.tableauAbsences[i] = 'F'; break;
                    }
                }
            })
        }
    }
}