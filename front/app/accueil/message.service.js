export default class MessageService{
		constructor($http, apiUrls){
			this.$http = $http
			this.urlGetMessages = apiUrls.message
		}
		
		getMessages(){
			return this.$http.get(this.urlGetMessages)
				.then(result => result.data)
		}
		
		getMessagesParDernièresSemaines(nbSemaines){
			return this.getMessages()
				.then(messages => {
					let messagesToSend = []
					let minDate = new Date()
					minDate.setDate(minDate.getDate()-nbSemaines*7)
					minDate = minDate.getTime()
					messages.forEach(msg => {
						let msgDate = new Date(msg.date.split('T')[0])
						msgDate = msgDate.getTime()
						if(msgDate >= minDate){
							let fDate = msg.date.split('T')[0]
							let fHeure = msg.date.split('T')[1]
							msg.date = 'Le '+fDate.split('-')[2]+'/'+fDate.split('-')[1]+'/'+fDate.split('-')[0]+' à '+fHeure.split(':')[0]+'h'+fHeure.split(':')[1]
							messagesToSend.push(msg)
						}
					})
					return messagesToSend
			})
		}
}