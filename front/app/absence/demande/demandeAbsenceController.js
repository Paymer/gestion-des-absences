export default class DemandeAbsenceCtrl{
    constructor(demandeAbsenceService,connexionService){ 

        this.demandeAbsenceService = demandeAbsenceService;
        this.connexionService = connexionService;


        this.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: true
        };

        this.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(),
            startingDay: 1
        };



        this.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        this.format = this.formats[0];
        this.altInputFormats = ['M!/d!/yyyy'];

        this.popup1 = {
            opened: false
        };

        this.popup2 = {
            opened: false
        };

        let tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        let afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 1);
        this.events = [
            {
            date: tomorrow,
            status: 'full'
            },
            {
            date: afterTomorrow,
            status: 'partially'
            }
        ];

        function getDayClass(data) {
            var date = data.date,
            mode = data.mode;
            if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0,0,0,0);

            for (var i = 0; i < this.events.length; i++) {
                var currentDay = new Date(this.events[i].date).setHours(0,0,0,0);

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
    return mode === 'day' && (date.getDay() <= 0);
  }
    }

    open1() {
         this.popup1.opened = true;
    };
    open2() {
        this.popup2.opened = true;
    };

  setDate(year, month, day) {
    this.dt = new Date(year, month, day);
  };
  today() {
    this.dt = new Date();
  };
 clear() {
    this.dt = null;
  };
    toggleMin() {
    this.inlineOptions.minDate = this.inlineOptions.minDate ? null : new Date();
    this.dateOptions.minDate = this.inlineOptions.minDate;
  };

  addAbsence(){
     
    //console.log(this.dtDebut,this.dtFin,this.type, this.motif)
    let absence = { dateDebut:this.dtDebut,dateFin:this.dtFin,type:this.type,motif: this.motif,idEmploye:this.connexionService.getMatricule()}
    
    this.demandeAbsenceService.confirmeEnvoiAbsence(absence)
    }

}