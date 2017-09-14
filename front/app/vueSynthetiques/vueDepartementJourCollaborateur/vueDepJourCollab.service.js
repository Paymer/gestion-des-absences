
export default class VueDepJourCollabService {
    constructor(apiUrls, $http, connexionService, visualisationAbsenceService) {
        this.apiUrls = apiUrls;
        this.$http = $http;
        this.connexionService = connexionService;
        this.visualisationAbsenceService = visualisationAbsenceService;
    }

    nombreJoursDuMois(moisCourant, anneeCourante) {
        switch(moisCourant) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if((anneeCourante % 4) == 0) {
                    return 29;
                }
                return 28;
        }
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

    getAbsencesParMatricule(matricule) {
        return this.$http.get(this.apiUrls.absence + "/" + matricule, {})
                  .then(result => {
                      return result.data;
                  }).then(absences => {
                        return this.$http.get(this.apiUrls.mission + "/" + matricule, {})
                            .then(result => {
                                this.visualisationAbsenceService.ajoutMissions(absences, result.data)
                                this.visualisationAbsenceService.transformerDate(absences);
                                return absences;
                            })
                    })
    }
}