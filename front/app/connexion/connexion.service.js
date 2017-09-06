export default class ConnexionService{
	
	connecter(matricule, nom, prenom, grade, email, departement){
		sessionStorage.setItem('session', true)
		sessionStorage.setItem('matricule', matricule)
		sessionStorage.setItem('nom', nom)
		sessionStorage.setItem('prenom', prenom)
		sessionStorage.setItem('grade', grade)
		sessionStorage.setItem('email', email)
		sessionStorage.setItem('departement', departement)
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
}