export default class ConnexionController{
	constructor($http, ConnexionService, apiUrl){
		this.$http = $http;
		this.connexion = ConnexionService;
		this.apiUrl = apiUrl
	}
	
	checkConnexion(){
		if(this.email && this.pwd){
			
		}
	}
}