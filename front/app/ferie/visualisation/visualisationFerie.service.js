import suppressionFerieTemplate from "./suppression/suppressionFerie.html"
import suppressionFerieController from "./suppression/suppressionFerie.controller"

export default class VisualisationFerieService {
	constructor(apiUrls, $http, $uibModal, connexionService) {
		this.apiUrls = apiUrls;
		this.$http = $http;
		this.$uibModal = $uibModal;
		this.connexionService = connexionService;
	}

	findAll() {
		return this.$http.get(this.apiUrls.ferie, {})
			.then(result => {
				return result.data;
			})
			.then(feries => {
				this.parseType(feries);
				this.transformerDate(feries);
				return feries;
			})
	}
	
	findAllAnnees(){
		return this.findAll().then(feries => {
			let annees = []
			feries.forEach(ferie => {
				if(annees.indexOf(ferie.date.split('/')[2]) === -1){
					annees.push(ferie.date.split('/')[2])
				}
			})
			return annees
		})
	}
	
	findByAnnee(annee){
		return this.findAll().then(feries => {
			let filteredFeries = []
			feries.forEach(ferie => {
				if(ferie.date.split('/')[2] == annee){
					filteredFeries.push(ferie)
				}
			})
			return filteredFeries
		})
	}

	parseType(feries) {
		feries.forEach(f => {
			switch(f.type){
				case 'RTT_EMPLOYEUR':
					f.type = 'RTT employeur'
					break;
				case 'JOUR_FERIE':
					f.type = 'Ferié'
					break;
			}
		})
	}

	transformerDate(feries) {
		feries.forEach(f => {
			f.dateOriginal = f.date;
		})

		feries.forEach(f => {
			let dArr = f.date.split("-");
			f.date = dArr[2] + "/" + dArr[1] + "/" + dArr[0];
		})
	}
	
	modifierFerie(ferie){
		this.modifId = ferie.id
		this.modifDate = ferie.date
		this.modifType = ferie.type
		this.modifCommentaires = ferie.commentaires
	}

    supprimerFerie(idFerie, date, type) {
        return this.$uibModal.open({
            template: suppressionFerieTemplate,
            controller: suppressionFerieController,
            controllerAs: '$ctrl',
            resolve: {
                idFerie: () => idFerie,
                date: () => date,
                type: () => type
            }
        });
    }

}