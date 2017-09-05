export default class ConnexionController{
	constructor($http, $location, connexionService, apiUrls){
		this.$http = $http;
		this.$location = $location
		this.connexion = connexionService;
		this.apiUrls = apiUrls
	}
	
	checkConnexion(){
		if(this.email && this.pwd){
			let url = this.apiUrls.connexion+'/'+this.email+'/'+this.pwd
			this.$http.get(url, {})
			.then(result => {
				return result.data
			}).then(user => {
					if(user.matricule){
						this.connexion.connecter(user.matricule, user.nom, user.prenom, user.grade, user.email, user.departement)
						this.$location.path('/')
					}else{
						this.error = true
					}
			})
		}
	}
}

ConnexionController.$inject = ['$http', '$location', 'connexionService', 'apiUrls'];