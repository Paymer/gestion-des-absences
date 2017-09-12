export default class ConnexionService{
	
	constructor(apiUrls, $http) {
		this.apiUrls = apiUrls;
		this.$http = $http;
	}

	connecter(matricule, nom, prenom, grade, email, departement, congesPayes, rtt){
		sessionStorage.setItem('session', true)
		sessionStorage.setItem('matricule', matricule)
		sessionStorage.setItem('nom', nom)
		sessionStorage.setItem('prenom', prenom)
		sessionStorage.setItem('grade', grade)
		sessionStorage.setItem('email', email)
		sessionStorage.setItem('departement', departement)
		sessionStorage.setItem('congesPayes', congesPayes)
		sessionStorage.setItem('rtt', rtt)
	}
	
	deconnecter(){
		sessionStorage.setItem('session', false)
		sessionStorage.clear()
	}
	
	isConnecte(){
		return sessionStorage.getItem('session')
	}
	
	getMatricule(){
		return sessionStorage.getItem('matricule')
	}
	
	getNom(){
		return sessionStorage.getItem('nom')
	}
	
	getPrenom(){
		return sessionStorage.getItem('prenom')
	}
	
	getGrade(){
		return sessionStorage.getItem('grade')
	}
	
	getEmail(){
		return sessionStorage.getItem('email')
	}
	
	getDepartement(){
		return sessionStorage.getItem('departement')
	}
	
	getCongesPayes(){
		return sessionStorage.getItem('congesPayes')
	}
	
	getRtt(){
		return sessionStorage.getItem('rtt')
	}

	getCongesPayesEtRttFromBase() {
		if(this.isConnecte()) {

			let matricule = this.getMatricule();

			return this.$http.get(this.apiUrls.congesEtRtt + "/" + matricule, {});
		}
	}

	setCongesPayes(congesPayes) {
		sessionStorage.setItem('congesPayes', congesPayes)
	}

	setRtt(rtt) {
		sessionStorage.setItem('rtt', rtt)
	}
}