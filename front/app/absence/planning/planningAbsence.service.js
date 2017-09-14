export default class PlanningAbsenceService {

    constructor(apiUrls, $http, $uibModal, connexionService) {
        this.apiUrls = apiUrls;
        this.$http = $http;
        this.$uibModal = $uibModal;
        this.connexionService = connexionService;
    }
    //toute les absence pour un utilisateur
    findAll() {
        let matriculeEmploye = this.connexionService.getMatricule();
        this.absences = this.$http.get(this.apiUrls.absence + "/" + matriculeEmploye, {})
            .then(result => {
                return result.data;
            })
            .then(absences => {
                
                this.parseType(absences)
                return absences;
            })

        return this.absences;
    }
    // tout les jour ferie avec les RTT employeur
    findAllFerier(){
		return this.$http.get(this.apiUrls.ferie, {})
        .then(result => {
            return result.data;
        })
        .then(feries => {
            this.parseType(feries);
            
            return feries;
        })
    }
    parseType(absence) {
		absence.forEach(f => {
			switch(f.type){
                case 'CONGES_PAYES':
                    f.type = 'Congés payés'
                    break;
                case 'CONGES_SANS_SOLDE':
                    f.type = 'Congés sans solde'
                    break;
                case 'MISSION':
                    f.type = 'Mission'
                    break;
				case 'RTT_EMPLOYEUR':
					f.type = 'RTT employeur'
					break;
				case 'JOUR_FERIE':
					f.type = 'Ferié'
					break;
			}
		})
	}
}