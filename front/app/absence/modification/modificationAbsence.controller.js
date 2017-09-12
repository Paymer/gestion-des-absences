export default class ModifAbsenceCtrl{
    
    constructor(modifAbsenceService,connexionService, $location, visualisationAbsenceService, $window){ 
        this.visualService = visualisationAbsenceService;
        //tout = 
        this.modifAbsenceService = modifAbsenceService;
        this.connexionService = connexionService;
        this.$location = $location;
        this.today = new Date();
        this.$window = $window;
        //identification of the absence to modify
        this.id = this.visualService.absenceModifId;

        //placeholders
        this.phInit =new Date(this.visualService.absenceModifInit);
        
        this.phFin = new Date(this.visualService.absenceModifFin);
        this.phType = this.visualService.absenceModifType;
        console.log(this.phInit);
        console.log(this.phFin);
        console.log(this.phType);

		this.titre = "Modification d'absence"

        this.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: true
        };

        this.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(this.today.getFullYear()+ 3, this.today.getMonth(), this.today.getDate()), 
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

  

    annuler(){
        this.$location.path("/absence");
    }

    verrifDateDebutInfDateFin(){
        if(this.dtDebut <= this.dtFin){
            return true;
        }else{
            return false;
        }
    }

    verrifDateDuJour(){
        
        if(this.dtDebut.getDate() === this.today.getDate() && this.dtDebut.getMonth() === this.today.getMonth() && this.dtDebut.getFullYear() === this.today.getFullYear() ){
           
            return true;
        }else{
            
            return false;
        }
    }

    motifPourCongeSansSolde(){
        
        if(this.type === "CONGES_SANS_SOLDE" && this.motif == undefined){
           
            return true;
        }else{
            return false;
        }

    }

    //call to the modif Absence service

addAbsence(){
    this.info ()
    let absence = {id: this.id, dateDebut:this.debut,dateFin:this.fin,type:this.type,motif: this.motif,matriculeEmploye:this.connexionService.getMatricule()}
    
    // console.log(absence)
     
    //console.log(this.dtDebut,this.dtFin,this.type, this.motif)
   
    this.modifAbsenceService.confirmeEnvoiAbsence(absence)
    .then((reponse) =>{
        if(reponse.succes){
            
            this.$location.path("/absence");
            this.$window.location.reload();
        }else{
            this.error = "erreur de l'ajout de l'absence"
            this.notvalid = true;
        }
       
    },() =>{
        this.error ="Server Problem"
        this.notvalid = true; 
    })
    this.$location.path("/absence");
    }



    info (){
        
                //Debut
                let yeard = this.dtDebut.getFullYear()
                let monthd= this.formatdate(this.dtDebut.getMonth()+1)
                let dayd = this.formatdate(this.dtDebut.getDate())
        
                //Fin
                let yearf = this.dtFin.getFullYear()
                let monthf= this.formatdate(this.dtFin.getMonth()+1)
                let dayf = this.formatdate(this.dtFin.getDate())
        
        
                this.debut =  yeard +"-"+monthd+"-"+dayd
                this.fin =  yearf +"-"+monthf+"-"+dayf
        
            }
        
            formatdate(number){
                if (number < 10){
                    return "0"+number;
                }
                else{
                    return number;
                }
            }

}



