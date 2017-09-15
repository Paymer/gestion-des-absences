export default class modificationFerieController {
	constructor($location, modifFerieService, connexionService,
		visualisationFerieService) {

		if (!visualisationFerieService.modifId) {
			$location.path('/ferie')
		} else {
			this.idFerie = visualisationFerieService.modifId
			this.dateOrigine = visualisationFerieService.modifDate
			this.date = new Date(this.dateOrigine.split('/')[2],
				this.dateOrigine.split('/')[1]-1,
				this.dateOrigine.split('/')[0])
			this.type = (visualisationFerieService.modifType == "Ferié")
				? 'JOUR_FERIE' : 'RTT_EMPLOYEUR'
			this.commentaires = visualisationFerieService.modifCommentaires
		}

		this.notvalid = false
		this.modificationFerieService = modifFerieService
		this.connexionService = connexionService
		this.$location = $location
		this.tomorrow = new Date()
		this.tomorrow.setDate(this.tomorrow.getDate() + 1)
		this.titre = "Modifier jour ferié / RTT employeur"
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

		this.formats = ['yyyy/MM/dd']
		this.format = this.formats[0]
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
					var currentDay = new Date(this.events[i].date).setHours(0,
						0, 0, 0);
					if (dayToCheck === currentDay) {
						return this.events[i].status;
					}
				}
			}

			return '';
		}

		function disabled() {
			return false;
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
		let retour = false
		if (this.date
			&& (
				this.date.getFullYear() > this.tomorrow.getFullYear()
				|| (this.date.getFullYear() == this.tomorrow.getFullYear()
					&& this.date.getMonth() > this.tomorrow.getMonth())
				|| (this.date.getFullYear() == this.tomorrow.getFullYear()
					&& this.date.getMonth() == this.tomorrow.getMonth()
					&& this.date.getDate() > this.tomorrow.getDate())
			)
		) {
			retour = true
		}
		return retour
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

	modifierFerie() {
		let data = {
			matricule: this.connexionService.getMatricule(),
			id: this.idFerie,
			date: this.date.getFullYear() + '-' + (this.date.getMonth() + 1) + '-' + this.date.getDate(),
			type: this.type,
			commentaires: this.commentaires || ""
		}
		this.error = false
		this.errorServer = false

		this.modificationFerieService.confirmeEnvoiFerie(data)
			.then(result => {
				if (result.data) {
					if (result.data.success) {
						this.$location.path('/ferie')
					} else {
						this.error = true
					}
				} else {
					this.errorServer = true
				}
			}, () => {
				this.errorServer = true
			})
	}
}