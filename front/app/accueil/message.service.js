export default class MessageService{
		constructor($http, $apiUrls){
			this.$http = $http
			this.urlGetMessages = $apiUrls.message
		}
		
		getMessages(){
			return this.$http.get(this.urlGetMessages)
				.then(result => result.data)
		}
		
		getMessagesDeuxDernièresSemaines(){
			return this.getMessages()
				.then(messages => {
					console.log(messages)
			})
		}
}