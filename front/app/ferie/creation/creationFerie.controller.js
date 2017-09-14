export default class creationFerieController {
	constructor($location, creationFerieService, connexionService) {
		this.notvalid = false
		this.creationFerieService = creationFerieService;
		this.connexionService = connexionService;
		this.$location = $location;
		this.tomorrow = new Date();
		this.tomorrow.setDate(this.tomorrow.getDate() + 1)
		this.titre = "Nouveau jour ferié / RTT employeur"
		this.error = false
		this.errorServer = false

		this.inlineOptions = {
			customClass: getDayClass,
			minDate: this.tomorrow,
			showWeeks: true
		}

		this.dateOptions = {
			dateDisabled: disabled,
			formatYear: 'yyyy',
			initDate: this.tomorrow,
			maxDate: new Date(this.tomorrow.getFullYear() + 3,
				this.tomorrow.getMonth(),
				this.tomorrow.getDate()),
			minDate: this.tomorrow,
			startingDay: 1
		}

		this.formats = [
			'yyyy/MM/dd'
		]
		this.format = this.formats[0];
		this.altInputFormats = [
			'd!/M!/yyyy'
		]

		this.popup = {
			opened: false
		}

		let afterTomorrow = new Date();
		afterTomorrow.setDate(this.tomorrow.getDate() + 1);
		this.events = [
			{
				date: this.tomorrow,
				status: 'full'
			},
			{
				date: afterTomorrow,
				status: 'partially'
			}
		]

		function getDayClass(data) {
			var date = data.date,
				mode = data.mode;
			if (mode === 'day') {
				var dayToCheck = new Date(date).setHours(0, 0, 0, 0);
				for (var i = 0; i < this.events.length; i++) {
					var currentDay = new Date(this.events[i].date).setHours(0, 0, 0, 0);
					if (dayToCheck === currentDay) {
						return this.events[i].status;
					}
				}
			}

			return '';
		}

		function disabled(data) {
			var date = data.date,
				mode = data.mode;
			return mode === 'day' && (date.getDay() <= 0 || date.getDay() >= 6);
		}
	}

//DATE PICKER
	open() {
		this.popup.opened = true;
	}
	setDate(year, month, day) {
		this.dt = new Date(year, month, day);
	}
	clear() {
		this.dt = null;
	}
	toggleMin() {
		this.inlineOptions.minDate = this.inlineOptions.minDate ? null : new Date()
		this.dateOptions.minDate = this.inlineOptions.minDate;
	}
//DATE PICKER END

	annuler() {
		this.$location.path("/ferie");
	}

	isDateValide() {
		if (this.date
			&& this.date.getDate() >= this.tomorrow.getDate()
			&& this.date.getMonth() >= this.tomorrow.getMonth()
			&& this.date.getFullYear() >= this.tomorrow.getFullYear()) {
			return true
		} else {
			return false
		}
	}

	isTypeValide() {
		return (this.type) ? true : false
	}

	isCommentaireValide() {

		if (this.type != "JOUR_FERIE" || (this.type === "JOUR_FERIE" && this.commentaires)) {
			return true
		} else {
			return false
		}

	}

	addFerie() {
		
		let data = {
			matricule: this.connexionService.getMatricule(),
			date: this.date.getFullYear()+'-'+(this.date.getMonth()+1)+'-'+this.date.getDate(),
			type: this.type,
			commentaires: this.commentaires || ""
		}
		
		this.error = false
		this.errorServer = false
		
		this.creationFerieService.confirmeEnvoiFerie(data)
			.then(result => {
				if(result){
					if(result.success){
						this.$location.path('/ferie')
					}else{
						this.error = true
					}
				}else{
					this.errorServer = true
				}
			}, () => {
				this.errorServer = true
			})
	}
}