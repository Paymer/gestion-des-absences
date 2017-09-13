export default class DemandeAbsenceCtrl{
    constructor(demandeAbsenceService,connexionService,$location){ 
        this.notvalid=false
        this.demandeAbsenceService = demandeAbsenceService;
        this.connexionService = connexionService;
        this.$location = $location;
        this.today = new Date();
		this.titre = "Demande d'absence"

        

        this.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: true
        };
        
        this.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(this.today.getFullYear()+ 3, this.today.getMonth(), this.today.getDate()), 
            minDate: new Date(this.today.getFullYear(), this.today.getMonth(), this.today.getDate()+1),
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
  // redirige vers la page visualisation absence
    annuler(){
        this.$location.path("/absence");
    }
    //return true si la date de debut est inférieure à la date de fin
    verrifDateDebutInfDateFin(){
        if(this.dtDebut <= this.dtFin){
            return true;
        }else{
            return false;
        }
    }
    //return true si la date selectionée et la date du jour
    verrifDateDuJour(){
        
        if(this.dtDebut.getDate() === this.today.getDate() && this.dtDebut.getMonth() === this.today.getMonth() && this.dtDebut.getFullYear() === this.today.getFullYear() ){
           
            return true;
        }else{
            
            return false;
        }
    }
    // return troue si Conges-sans-solde est selectionnée
    motifPourCongeSansSolde(){
        
        if(this.type === "CONGES_SANS_SOLDE" && this.motif == undefined){
           
            return true;
        }else{
            return false;
        }

    }

//permet l'envois d'une absence par la méthode Post
 addAbsence(){
    
    this.info ()
    let absence = { dateDebut:this.debut,dateFin:this.fin,type:this.type,motif: this.motif,matriculeEmploye:this.connexionService.getMatricule()}

    
    this.demandeAbsenceService.confirmeEnvoiAbsence(absence)
    .then((reponse) =>{
        console.log(reponse.succes)
        if(reponse.succes){
            
            this.$location.path("/absence");
        }else{
            this.error ="erreur de l'ajout de l'absence"
            this.notvalid = true;
        }
       
    },() =>{
        this.error ="Server Problem"
        this.notvalid = true; 
    })

    }


    //permet de formater la date debut et date de fin "yyyy-MM-dd"  
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
    // formate le chiffre du mois et du jour ex:jour 1 -> jour 01
    formatdate(number){
        if (number < 10){
            return "0"+number;
        }
        else{
            return number;
        }
    }
}