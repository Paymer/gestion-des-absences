export default class AccueilController{
    constructor(messageService) {
		this.messages = messageService
		
        this.titre = "Bienvenue sur l'application GDA - Gestion Des Absences"
    }
}