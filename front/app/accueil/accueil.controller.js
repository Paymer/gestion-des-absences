export default class AccueilController {
	constructor(messageService) {
		this.messageService = messageService

		this.titre = "Bienvenue sur l'application GDA - Gestion Des Absences"

		this.messages = []
		
		this.messageDelai = 2
		this.rangeValues = this.range(this.messageDelai, 100)
		this.loadMessages()
		
	}

	loadMessages() {
		this.messageService.getMessagesParDernièresSemaines(this.messageDelai)
			.then(messages => this.messages = messages)
	}

	range(min, max, step = 1) {
		var input = []
		for (var i = min; i <= max; i += step) {
			input.push(i)
		}
		return input
	}
}